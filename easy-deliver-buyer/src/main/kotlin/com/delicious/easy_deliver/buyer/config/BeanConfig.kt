package com.delicious.easy_deliver.buyer.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
object ObjMapper: ObjectMapper()

@Component
open class BeanConfig(private val objectMapper: ObjectMapper):CommandLineRunner {
  override fun run(vararg args: String?) {
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    objectMapper.registerModule(JavaTimeModule())
  }
}




