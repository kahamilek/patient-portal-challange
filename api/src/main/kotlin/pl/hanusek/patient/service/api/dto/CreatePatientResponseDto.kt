package pl.hanusek.patient.service.api.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
sealed class CreatePatientResponseDto {

    @JsonTypeName("SUCCESS")
    data class Success(
        val patientId: String
    ): CreatePatientResponseDto()

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ): CreatePatientResponseDto()
}
