package pl.hanusek.patient.service.api.doctor.dto

data class CreateDoctorRequestDto(
    val fullName: FullName,
    val specializationName: String,
    val organizationName: String
)
