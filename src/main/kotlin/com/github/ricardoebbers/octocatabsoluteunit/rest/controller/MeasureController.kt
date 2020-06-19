package com.github.ricardoebbers.octocatabsoluteunit.rest.controller

import com.github.ricardoebbers.octocatabsoluteunit.rest.query.MeasureQuery
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/measure")
class MeasureController {
    @PostMapping
    fun measureRepository(@RequestBody query: MeasureQuery): String = query.repositoryUrl.toString()
}
