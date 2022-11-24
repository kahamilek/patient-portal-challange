package pl.hanusek.patient.service.api.doctor_appointment.dto

import java.time.LocalTime

data class ChangeDoctorsAppointmentTimeRequestDto(
    val newTime: LocalTime
)
