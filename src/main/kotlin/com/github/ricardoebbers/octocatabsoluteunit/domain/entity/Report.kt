package com.github.ricardoebbers.octocatabsoluteunit.domain.entity

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Report(
        @Id
        val uri: String,
        @OneToMany(cascade = [CascadeType.ALL])
        val measurements: MutableMap<String, Measurement> = mutableMapOf(),
        var totalLines: Int = 0,
        var totalSloc: Int = 0,
        var totalBytes: Int = 0
) {
    fun addMeasurements(measurement: Measurement) {
        measurements.merge(measurement.extension, measurement) { m1, m2 -> m1.add(m2) }
        totalLines += measurement.lines
        totalSloc += measurement.sloc
        totalBytes += measurement.bytes
    }
}
