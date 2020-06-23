package com.github.ricardoebbers.octocatabsoluteunit.domain.strategy

import org.jsoup.nodes.Document

class MeasureExecutableStrategy : MeasureStrategy() {
    override fun splitData(data: String): List<String> {
        return data
                .split("executable file ", " lines (", " sloc) ")
                .filter { it.isNotBlank() }
    }

    override fun getData(doc: Document): String {
        return doc.select(".file-mode").parents().first().text()
    }
}
