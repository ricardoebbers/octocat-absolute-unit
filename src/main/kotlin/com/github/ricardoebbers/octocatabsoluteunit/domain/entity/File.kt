package com.github.ricardoebbers.octocatabsoluteunit.domain.entity

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

data class File(
        override val uri: String,
        override var isVisited: Boolean = false
) : Scrappable {
    override lateinit var data: String
    lateinit var measurement: Measurement

    fun calculateMeasurements() {
        val doc: Document = Jsoup.parse(data)
        val fileType = defineFileType(doc)
        measurement = fileType.getMeasurement(this)
    }

    private fun defineFileType(doc: Document): FileTypeEnum {
        return FileTypeEnum.values().first { it.isOfType(doc) }
    }


}
