package pl.hanusek.patient.service.api.dto

data class UpdatePatientRequestDto(
    val fullName: CreatePatientRequestDto.FullName,
    val address: CreatePatientRequestDto.Address,
)
