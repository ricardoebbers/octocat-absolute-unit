package com.github.ricardoebbers.octocatabsoluteunit.domain.entity

interface Scrappable {
    val uri: String
    var data: String
    var isVisited: Boolean

    fun visit(data: String) {
        this.data = data
        isVisited = true
    }
}
