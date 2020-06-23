package com.github.ricardoebbers.octocatabsoluteunit.rest.client.impl

import com.github.ricardoebbers.octocatabsoluteunit.rest.client.RequestClient
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URLDecoder
import java.util.concurrent.CompletableFuture

@Service
class RequestClientImpl : RequestClient {

    companion object {
        private const val BASE_URL = "https://www.github.com"
    }

    @Async
    override fun get(uri: String): CompletableFuture<String> {
        val restTemplate = RestTemplate()
        val url = URLDecoder.decode("$BASE_URL$uri", "UTF-8")
        return CompletableFuture.completedFuture(restTemplate.getForObject(url))
    }
}
