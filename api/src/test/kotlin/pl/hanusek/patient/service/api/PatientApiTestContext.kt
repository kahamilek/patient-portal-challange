package pl.hanusek.patient.service.api

import org.springframework.http.ResponseEntity
import pl.hanusek.patient.service.api.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.dto.CreatePatientResponseDto
import pl.hanusek.patient.service.domain.patient.PatientTestContext

class PatientApiTestContext {

    private val patientsFacade = PatientTestContext().patientsFacade
    private val patientsController = PatientsController(patientsFacade)

    fun createPatient(createPatientRequestDto: CreatePatientRequestDto): ResponseEntity<CreatePatientResponseDto> {
        return patientsController.createPatient(createPatientRequestDto)
    }

}
