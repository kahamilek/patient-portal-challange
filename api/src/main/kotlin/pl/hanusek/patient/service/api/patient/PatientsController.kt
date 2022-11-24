package pl.hanusek.patient.service.api.patient

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.hanusek.patient.service.api.patient.dto.*
import pl.hanusek.patient.service.domain.OrderType.Companion.DEFAULT_ORDER_TYPE_TEXT
import pl.hanusek.patient.service.domain.OrderType.Companion.from
import pl.hanusek.patient.service.domain.patient.Patient
import pl.hanusek.patient.service.domain.patient.PatientInvalidArgumentException
import pl.hanusek.patient.service.domain.patient.PatientNotFoundException
import pl.hanusek.patient.service.domain.patient.PatientsFacade

@RestController
class PatientsController(
    private val patientsFacade: PatientsFacade
) {


    @PostMapping("/api/v1/patients")
    fun createPatient(@RequestBody request: CreatePatientRequestDto): ResponseEntity<CreatePatientResponseDto> =
        kotlin.runCatching {
            val patient = patientsFacade.createPatient(
                patientFullName = request.fullName.toDomain(),
                patientAddress = request.address.toDomain(),
                organizationName = request.organizationName.toDomain()
            )
            ResponseEntity.ok(patient.toDtoModel())
        }.getOrElse {
            if (it is PatientInvalidArgumentException) {
                ResponseEntity.badRequest()
                    .body(CreatePatientResponseDto.Error(localizedErrorMessage(it.errorType)))
            } else {
                logger.error(it) { "Error occurred while adding new patient" }
                ResponseEntity.internalServerError()
                    .body(CreatePatientResponseDto.Error(UNEXPECTED_ERROR_MESSAGE))
            }
        }

    @PutMapping("/api/v1/patients/{patientId}")
    fun updatePatient(
        @PathVariable("patientId") patientId: String,
        @RequestBody updatePatientRequest: UpdatePatientRequestDto
    ): ResponseEntity<out UpdatePatientResponseDto> = kotlin.runCatching {
        patientsFacade.updatePatient(
            patientId = Patient.PatientId.from(patientId),
            patientWithNewData = updatePatientRequest.toDomainModel()
        )
        ResponseEntity.ok(UpdatePatientResponseDto.Success())
    }.getOrElse {
        when (it) {
            is PatientInvalidArgumentException -> {
                ResponseEntity.badRequest()
                    .body(UpdatePatientResponseDto.Error(localizedErrorMessage(it.errorType)))
            }
            is PatientNotFoundException -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(UpdatePatientResponseDto.Error("No patient with specified id found"))
            else -> {
                logger.error(it) { "Error occurred while updating patient with id $patientId" }
                ResponseEntity.internalServerError()
                    .body(UpdatePatientResponseDto.Error(UNEXPECTED_ERROR_MESSAGE))
            }
        }
    }

    @GetMapping("/api/v1/patients")
    fun getPatients(
        @RequestParam("page_number", required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam("page_size", required = false, defaultValue = "20") pageSize: Int,
        @RequestParam("order_type", required = false, defaultValue = DEFAULT_ORDER_TYPE_TEXT) orderTypeText: String
    ): ResponseEntity<GetPatientsResponseDto> = kotlin.runCatching {
        patientsFacade.getPatients(pageNumber, pageSize, from(orderTypeText))
            .toDtoModel()
    }.getOrElse {
        logger.error(it) { "Unexpected error occurred" }
        ResponseEntity.internalServerError()
            .body(GetPatientsResponseDto.Error(UNEXPECTED_ERROR_MESSAGE))
    }

    @DeleteMapping("/api/v1/patients/{patientId}")
    fun removePatient(@PathVariable patientId: String): ResponseEntity<out RemovePatientResponseDto> = kotlin.runCatching {
        patientsFacade.removePatient(Patient.PatientId.from(patientId))
        ResponseEntity.ok(RemovePatientResponseDto.Success())
    }.getOrElse {
        logger.error(it) { "Unexpected error occurred" }
        ResponseEntity.internalServerError()
            .body(RemovePatientResponseDto.Error(UNEXPECTED_ERROR_MESSAGE))
    }

}

private const val UNEXPECTED_ERROR_MESSAGE = "Unexpected error occurred on server side. Please try later."

private val logger = KotlinLogging.logger { }

private fun localizedErrorMessage(errorType: PatientInvalidArgumentException.ErrorType): String {
    return when (errorType) {
        PatientInvalidArgumentException.ErrorType.FIRST_NAME_IS_BLANK -> "Missing first name"
        PatientInvalidArgumentException.ErrorType.LAST_NAME_IS_BLANK -> "Missing last name"
    }
}
