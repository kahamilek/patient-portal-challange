package pl.hanusek.patient.service.api

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.hanusek.patient.service.api.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.dto.CreatePatientResponseDto
import pl.hanusek.patient.service.api.dto.UpdatePatientRequestDto
import pl.hanusek.patient.service.api.dto.UpdatePatientResponseDto
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
                    .body(CreatePatientResponseDto.Error("Unexpected error occurred on server side. Please try later."))
            }
        }

    @PutMapping("/api/v1/patients/{patientId}")
    fun updatePatient(
        @PathVariable("patientId") patientId: String,
        @RequestBody updateRequest: UpdatePatientRequestDto
    ): ResponseEntity<out UpdatePatientResponseDto> = kotlin.runCatching {
        patientsFacade.updatePatient(
            patientId = Patient.PatientId.from(patientId),
            patientWithNewData = updateRequest.toDomainModel()
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
                    .body(UpdatePatientResponseDto.Error("Unexpected error occurred on server side. Please try later."))
            }
        }
    }

}

private val logger = KotlinLogging.logger { }

private fun localizedErrorMessage(errorType: PatientInvalidArgumentException.ErrorType): String {
    return when (errorType) {
        PatientInvalidArgumentException.ErrorType.FIRST_NAME_IS_BLANK -> "Missing first name"
        PatientInvalidArgumentException.ErrorType.LAST_NAME_IS_BLANK -> "Missing last name"
    }
}
