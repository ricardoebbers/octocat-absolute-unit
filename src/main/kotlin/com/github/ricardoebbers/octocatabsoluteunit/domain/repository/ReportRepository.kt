package com.github.ricardoebbers.octocatabsoluteunit.domain.repository

import com.github.ricardoebbers.octocatabsoluteunit.domain.entity.Report
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReportRepository : JpaRepository<Report, String> {
    fun findOneByUri(uri: String): Optional<Report>
}
