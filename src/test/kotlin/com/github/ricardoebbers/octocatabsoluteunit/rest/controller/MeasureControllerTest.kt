package com.github.ricardoebbers.octocatabsoluteunit.rest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.ricardoebbers.octocatabsoluteunit.rest.query.MeasureQuery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.net.URL

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class MeasureControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun testMeasure() {
        val query = MeasureQuery(repositoryUrl = URL("https://github.com/ricardoebbers/octocat-absolute-unit"))
        mockMvc.perform(post("/measure")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(query)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isNotEmpty)
                .andExpect(jsonPath("$.uri").value("/ricardoebbers/octocat-absolute-unit"))
                .andExpect(jsonPath("$.totalLines").isNumber)
                .andExpect(jsonPath("$.totalSloc").isNumber)
                .andExpect(jsonPath("$.totalBytes").isNumber)
                .andExpect(jsonPath("$.measurements").isMap)
                .andExpect(jsonPath("$.measurements").isNotEmpty)

    }

    private fun asJsonString(obj: Any): String {
        return try {
             ObjectMapper().writeValueAsString(obj)
        } catch (ex: Exception) {
            throw ex
        }
    }
}

