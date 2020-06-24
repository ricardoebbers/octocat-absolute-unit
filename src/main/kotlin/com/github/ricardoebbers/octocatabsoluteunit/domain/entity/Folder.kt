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
        val elements = doc.select(".js-details-container .Box-row .js-navigation-open")
        children = elements.filter { it.hasAttr("rel").not() }
                .mapNotNull { createChildFromUri(it.attr("href")) }.toList()
    }

    private fun createChildFromUri(uri: String): Scrappable? {
        return when {
            uri.contains("tree") -> Folder(uri)
            uri.contains("blob") -> File(uri)
            else -> null
        }
    }

}
