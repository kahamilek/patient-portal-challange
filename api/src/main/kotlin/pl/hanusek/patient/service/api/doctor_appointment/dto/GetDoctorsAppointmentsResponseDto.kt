package pl.hanusek.patient.service.api.doctor_appointment.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "state"
)
sealed class GetDoctorsAppointmentsResponseDto {

    @JsonTypeName("SUCCESS")
    data class Success(
        val doctorsAppointments: List<DoctorsAppointmentDto>,
        val pageNumber: Int,
        val pageSize: Int,
        val totalNumberOfPages: Int
    ) : GetDoctorsAppointmentsResponseDto()

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ) : GetDoctorsAppointmentsResponseDto()
}
