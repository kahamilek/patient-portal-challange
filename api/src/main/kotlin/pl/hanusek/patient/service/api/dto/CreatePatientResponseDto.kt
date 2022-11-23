package pl.hanusek.patient.service.api.dto

sealed class CreatePatientResponseDto {

    data class Success(
        val patientId: String
    ): CreatePatientResponseDto()

    data class Error(
        val localizedMessage: String
    ): CreatePatientResponseDto()
}
