package com.github.ricardoebbers.octocatabsoluteunit.domain.strategy

import org.jsoup.nodes.Document

class MeasureBinaryStrategy : MeasureStrategy() {
    override fun splitData(data: String): List<String> {
        return listOf("0", "0", data)
    }

    override fun getData(doc: Document): String {
        return doc.select(".Box-header > .text-mono").parents().first().text()
    }
}
