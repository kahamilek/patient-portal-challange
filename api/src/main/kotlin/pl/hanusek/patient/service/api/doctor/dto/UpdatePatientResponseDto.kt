package pl.hanusek.patient.service.api.doctor.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "state"
)
sealed class UpdateDoctorResponseDto {

    @JsonTypeName("SUCCESS")
    class Success : UpdateDoctorResponseDto()

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ) : UpdateDoctorResponseDto()
}
