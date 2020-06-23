package com.github.ricardoebbers.octocatabsoluteunit.domain.service

import com.github.ricardoebbers.octocatabsoluteunit.domain.entity.Report
import java.net.URL

interface ScrapingService {
    fun fetchOrScrape(baseUri: URL): Report
}
