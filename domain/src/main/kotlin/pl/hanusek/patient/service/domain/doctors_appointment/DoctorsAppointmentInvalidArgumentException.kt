package pl.hanusek.patient.service.domain.doctors_appointment

class DoctorsAppointmentInvalidArgumentException(
    val errorType: ErrorType
) : IllegalArgumentException("Cannot create doctors appointment, cause: $errorType") {

    enum class ErrorType {
        DOCTOR_WITH_SPECIFIED_ID_NOT_FOUND,
        PATIENT_WITH_SPECIFIED_ID_NOT_FOUND,
        PATIENT_AND_DOCTOR_ARE_IN_DIFFERENT_ORGANIZATIONS,
    }

    companion object {
        fun noDoctor() = DoctorsAppointmentInvalidArgumentException(ErrorType.DOCTOR_WITH_SPECIFIED_ID_NOT_FOUND)
        fun noPatient() = DoctorsAppointmentInvalidArgumentException(ErrorType.PATIENT_WITH_SPECIFIED_ID_NOT_FOUND)
        fun differentOrganizations() = DoctorsAppointmentInvalidArgumentException(ErrorType.PATIENT_AND_DOCTOR_ARE_IN_DIFFERENT_ORGANIZATIONS)
    }
}
