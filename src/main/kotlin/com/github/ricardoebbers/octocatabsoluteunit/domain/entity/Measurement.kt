package com.github.ricardoebbers.octocatabsoluteunit.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Measurement(
        @Id
        @GeneratedValue
        @JsonIgnore
        private val id: Long? = null,
        @JsonIgnore
        val extension: String,
        var lines: Int = 0,
        var sloc: Int = 0,
        var bytes: Int = 0
) {
    fun add(other: Measurement): Measurement {
        lines += other.lines
        sloc += other.sloc
        bytes += other.bytes
        return this
    }
}
