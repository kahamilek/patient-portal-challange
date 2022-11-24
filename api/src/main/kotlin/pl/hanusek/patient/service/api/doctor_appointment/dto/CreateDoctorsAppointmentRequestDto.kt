package pl.hanusek.patient.service.api.doctor_appointment.dto

import java.time.LocalDate
import java.time.LocalTime

data class CreateDoctorsAppointmentRequestDto(
    val date: LocalDate,
    val time: LocalTime,
    val place: String,
    val attendingPhysicianId: String,
    val patientId: String
)
