package com.github.ricardoebbers.octocatabsoluteunit.domain.entity

import com.github.ricardoebbers.octocatabsoluteunit.domain.strategy.MeasureBinaryStrategy
import com.github.ricardoebbers.octocatabsoluteunit.domain.strategy.MeasureExecutableStrategy
import com.github.ricardoebbers.octocatabsoluteunit.domain.strategy.MeasureStrategy
import com.github.ricardoebbers.octocatabsoluteunit.domain.strategy.MeasureTextStrategy
import org.jsoup.nodes.Document

enum class FileTypeEnum(val strategy: MeasureStrategy, private val isOfTypeSelector: String) {
    EXECUTABLE(MeasureExecutableStrategy(), ".file-mode"),
    TEXT(MeasureTextStrategy(), ".file-info-divider"),
    BINARY(MeasureBinaryStrategy(), "div[itemprop=text] > div > a"),
    IMAGE(MeasureBinaryStrategy(), "div[itemprop=text] > div > span > img");

    fun isOfType(doc: Document) = doc.select(isOfTypeSelector).isNotEmpty()
    fun getMeasurement(file: File) = this.strategy.measure(file)
}
