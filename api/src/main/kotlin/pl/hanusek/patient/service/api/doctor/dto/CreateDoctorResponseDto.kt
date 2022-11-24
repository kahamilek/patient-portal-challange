package pl.hanusek.patient.service.api.doctor.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "state"
)
sealed class CreateDoctorResponseDto {

    @JsonTypeName("SUCCESS")
    data class Success(
        val doctorId: String
    ): CreateDoctorResponseDto()

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ): CreateDoctorResponseDto()
}
