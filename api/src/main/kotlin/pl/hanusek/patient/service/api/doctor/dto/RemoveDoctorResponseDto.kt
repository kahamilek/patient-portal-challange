package pl.hanusek.patient.service.api.doctor.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "state"
)
sealed class RemoveDoctorResponseDto {

    @JsonTypeName("SUCCESS")
    class Success: RemoveDoctorResponseDto()

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ): RemoveDoctorResponseDto()
}
