package pl.hanusek.patient.service.api.doctor.dto

data class UpdateDoctorRequestDto(
    val fullName: FullName,
    val specializationName: String,
)
