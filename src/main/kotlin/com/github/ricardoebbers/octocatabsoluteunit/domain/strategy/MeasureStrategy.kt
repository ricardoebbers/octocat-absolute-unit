package com.github.ricardoebbers.octocatabsoluteunit.domain.strategy

import com.github.ricardoebbers.octocatabsoluteunit.domain.entity.File
import com.github.ricardoebbers.octocatabsoluteunit.domain.entity.Measurement
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.math.BigDecimal

abstract class MeasureStrategy {
    fun measure(file: File): Measurement {
        val doc: Document = Jsoup.parse(file.data)
        val data = getData(doc)
        return extractMeasurements(data, file.uri)
    }

    private fun extractMeasurements(data: String, uri: String): Measurement {
        val valueList = splitData(data)
        return Measurement(
                extension = getExtensionFromUri(uri),
                lines = Integer.valueOf(valueList[0]),
                sloc = Integer.valueOf(valueList[1]),
                bytes = calculateBytesFromText(valueList[2])
        )
    }

    private fun calculateBytesFromText(text: String): Int {
        val splittedText = text.split(" ")
        val value = BigDecimal(splittedText[0])
        val unit = splittedText[1]
        val scale = BigDecimal.valueOf(1024)
        return when (unit) {
            "Bytes" -> value
            "KB" -> value * scale
            "MB" -> value * scale * scale
            "GB" -> value * scale * scale * scale
            else -> throw IllegalArgumentException()
        }.toInt()
    }

    private fun getExtensionFromUri(uri: String): String {
        return uri.split(".").getOrElse(1) { "" }
    }

    abstract fun getData(doc: Document): String

    abstract fun splitData(data: String): List<String>
}
