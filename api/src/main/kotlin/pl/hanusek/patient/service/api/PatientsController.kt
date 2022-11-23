package pl.hanusek.patient.service.api

import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.hanusek.patient.service.api.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.dto.CreatePatientResponseDto
import pl.hanusek.patient.service.domain.organization.Organization
import pl.hanusek.patient.service.domain.patient.Patient
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
            logger.error (it){ "Error occurred while adding new patient" }
            ResponseEntity.internalServerError()
                .body(CreatePatientResponseDto.Error("Unexpected error occurred on server side. Please try later."))
        }
}

private val logger = KotlinLogging.logger { }

private fun String.toDomain(): Organization.OrganizationName {
    return Organization.OrganizationName.from(this)
}

private fun Patient.toDtoModel(): CreatePatientResponseDto {
    return CreatePatientResponseDto.Success(this.id.value)
}

private fun CreatePatientRequestDto.Address.toDomain(): Patient.Address {
    return Patient.Address(
        city = city,
        postcode = postcode,
        street = street,
        buildingNumber = buildingNumber,
        apartmentNumber = apartmentNumber,
    )
}

private fun CreatePatientRequestDto.FullName.toDomain(): Patient.FullName {
    return Patient.FullName(
        firstName = firstName,
        lastName = lastName,
    )
}
