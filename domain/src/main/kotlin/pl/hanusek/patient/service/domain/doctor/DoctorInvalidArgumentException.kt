package pl.hanusek.patient.service.domain.doctor

class DoctorInvalidArgumentException(
    val errorType: ErrorType
) : IllegalArgumentException("Cannot create doctor, cause: $errorType") {

    enum class ErrorType {
        FIRST_NAME_IS_BLANK,
        LAST_NAME_IS_BLANK
    }
}
