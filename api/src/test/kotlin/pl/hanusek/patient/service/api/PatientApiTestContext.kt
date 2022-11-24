package pl.hanusek.patient.service.api

import org.springframework.http.ResponseEntity
import pl.hanusek.patient.service.api.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.dto.CreatePatientResponseDto
import pl.hanusek.patient.service.api.dto.UpdatePatientRequestDto
import pl.hanusek.patient.service.api.dto.UpdatePatientResponseDto
import pl.hanusek.patient.service.domain.patient.PatientTestContext

class PatientApiTestContext {

    private val patientsFacade = PatientTestContext().patientsFacade
    private val patientsController = PatientsController(patientsFacade)

    fun createPatient(createPatientRequestDto: CreatePatientRequestDto): ResponseEntity<CreatePatientResponseDto> {
        return patientsController.createPatient(createPatientRequestDto)
    }

    fun updatePatient(patientId: String, updatePatientRequest: UpdatePatientRequestDto): ResponseEntity<out UpdatePatientResponseDto> {
        return patientsController.updatePatient(patientId, updatePatientRequest)
    }

}

fun initializedEmptyPatientApiContext(): PatientApiTestContext {
    return PatientApiTestContext()
}
