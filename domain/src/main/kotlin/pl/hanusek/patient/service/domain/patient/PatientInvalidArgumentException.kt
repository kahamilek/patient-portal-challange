package pl.hanusek.patient.service.domain.patient

class PatientInvalidArgumentException(
    val errorType: ErrorType
) : IllegalArgumentException("Cannot create patient, cause: $errorType") {

    enum class ErrorType {
        FIRST_NAME_IS_BLANK,
        LAST_NAME_IS_BLANK
    }
}
