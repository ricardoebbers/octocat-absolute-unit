package com.github.ricardoebbers.octocatabsoluteunit.domain.strategy

import org.jsoup.nodes.Document

class MeasureTextStrategy : MeasureStrategy() {
    override fun splitData(data: String) = data.split(" lines (", " sloc) ")

    override fun getData(doc: Document): String {
        return doc.select(".file-info-divider").parents().first().text()
    }
}
