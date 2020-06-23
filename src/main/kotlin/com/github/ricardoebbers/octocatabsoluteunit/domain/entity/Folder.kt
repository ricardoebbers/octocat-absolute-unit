package com.github.ricardoebbers.octocatabsoluteunit.domain.entity

import org.jsoup.Jsoup

data class Folder(
        override val uri: String
) : Scrappable {
    override lateinit var data: String
    override var isVisited: Boolean = false
    lateinit var children: List<Scrappable>

    fun extractChildrenFromData() {
        val doc = Jsoup.parse(data)
        val elements = doc.select("table.files td.content a")
        children = elements.map { createChildFromUri(it.attr("href")) }.toList()
    }

    private fun createChildFromUri(uri: String): Scrappable {
        return if (uri.contains("tree")) Folder(uri) else File(uri)
    }

}
