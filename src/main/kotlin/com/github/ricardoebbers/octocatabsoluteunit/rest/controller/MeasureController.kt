package com.github.ricardoebbers.octocatabsoluteunit.rest.controller

import com.github.ricardoebbers.octocatabsoluteunit.domain.entity.Report
import com.github.ricardoebbers.octocatabsoluteunit.domain.service.ScrapingService
import com.github.ricardoebbers.octocatabsoluteunit.rest.query.MeasureQuery
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/measure")
class MeasureController(
        private val scrapingService: ScrapingService
) {
    @PostMapping
    fun measureRepository(@RequestBody query: MeasureQuery): Report {
        return scrapingService.fetchOrScrape(query.repositoryUrl)
    }
}
