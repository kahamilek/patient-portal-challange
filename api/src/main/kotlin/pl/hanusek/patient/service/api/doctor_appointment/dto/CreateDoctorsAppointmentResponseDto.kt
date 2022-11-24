package pl.hanusek.patient.service.api.doctor_appointment.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "state"
)
sealed class CreateDoctorsAppointmentResponseDto {

    @JsonTypeName("SUCCESS")
    data class Success(
        val doctorsAppointment: DoctorsAppointmentDto
    ) : CreateDoctorsAppointmentResponseDto()


}
