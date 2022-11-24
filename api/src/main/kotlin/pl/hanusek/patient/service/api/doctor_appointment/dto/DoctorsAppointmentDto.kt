package pl.hanusek.patient.service.api.doctor_appointment.dto

import pl.hanusek.patient.service.api.FullName
import pl.hanusek.patient.service.domain.doctors_appointment.DoctorsAppointment
import java.time.LocalDate
import java.time.LocalTime

data class DoctorsAppointmentDto(
    val id: String,
    val date: LocalDate,
    val time: LocalTime,
    val place: String,
    val patientFullName: FullName,
    val doctorFullName: FullName
)
