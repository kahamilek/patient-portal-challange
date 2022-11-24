package pl.hanusek.patient.service.domain

import pl.hanusek.patient.service.domain.patient.PatientInvalidArgumentException

data class FullName(
    val firstName: String,
    val lastName: String
) {
    init {
        if (firstName.isBlank()) {
            throw PatientInvalidArgumentException(PatientInvalidArgumentException.ErrorType.FIRST_NAME_IS_BLANK)
        }
        if (lastName.isBlank()) {
            throw PatientInvalidArgumentException(PatientInvalidArgumentException.ErrorType.LAST_NAME_IS_BLANK)
        }
    }
}
