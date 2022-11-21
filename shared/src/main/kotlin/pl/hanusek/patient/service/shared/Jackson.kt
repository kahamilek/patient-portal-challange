package pl.hanusek.patient.service.shared

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object Jackson {
    val mapper = jacksonObjectMapper()
        .registerModule(JavaTimeModule()).also {
            it.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            it.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            it.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
            it.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
            it.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            it.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
            it.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true)
        }
}
