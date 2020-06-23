package com.github.ricardoebbers.octocatabsoluteunit.rest.client

import java.util.concurrent.CompletableFuture

interface RequestClient {
    fun get(uri: String): CompletableFuture<String>
}
