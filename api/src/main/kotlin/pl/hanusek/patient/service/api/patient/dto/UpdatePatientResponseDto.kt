package pl.hanusek.patient.service.api.patient.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
sealed class UpdatePatientResponseDto {

    @JsonTypeName("SUCCESS")
    class Success : UpdatePatientResponseDto()

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ) : UpdatePatientResponseDto()
}
