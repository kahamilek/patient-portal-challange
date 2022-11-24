package pl.hanusek.patient.service.api.patient.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "state"
)
sealed class RemovePatientResponseDto {

    @JsonTypeName("SUCCESS")
    class Success: RemovePatientResponseDto()

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ): RemovePatientResponseDto()
}
