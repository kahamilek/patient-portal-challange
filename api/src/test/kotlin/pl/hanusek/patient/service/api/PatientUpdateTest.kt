package pl.hanusek.patient.service.api

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.hanusek.patient.service.api.dto.CreatePatientResponseDto
import pl.hanusek.patient.service.api.dto.UpdatePatientResponseDto
import pl.hanusek.patient.service.api.fixtures.*
import pl.hanusek.patient.service.api.fixtures.INVALID_PATIENT_ID
import pl.hanusek.patient.service.api.fixtures.PATIENT_UPDATE_MODEL_WITH_BLANK_FIRST_NAME
import pl.hanusek.patient.service.api.fixtures.VALID_PATIENT_CREATION_MODEL
import pl.hanusek.patient.service.api.fixtures.VALID_PATIENT_UPDATE_MODEL

class PatientUpdateTest : BehaviorSpec({
    given("Initialized patient API context") {
        val patientContext = initializedEmptyPatientApiContext()
        When("Update not existing patient with valid model") {
            val patientUpdateResult = patientContext.updatePatient(INVALID_PATIENT_ID, VALID_PATIENT_UPDATE_MODEL)
            Then("The patient shouldn't be founded") {
                patientUpdateResult.statusCode shouldBe HttpStatus.NOT_FOUND
                (patientUpdateResult.body as UpdatePatientResponseDto.Error).shouldNotBeNull()
            }

        }
        and("Created patient") {
            val patientId = getPatientId(patientContext.createPatient(VALID_PATIENT_CREATION_MODEL))
            When("Update patient with valid model") {
                val patientUpdateResult = patientContext.updatePatient(patientId, VALID_PATIENT_UPDATE_MODEL)
                Then("Result is success") {
                    patientUpdateResult.statusCode shouldBe HttpStatus.OK
                }

            }
            When("Update patient with blank first name") {
                val patientUpdateResult = patientContext.updatePatient(patientId, PATIENT_UPDATE_MODEL_WITH_BLANK_FIRST_NAME)
                Then("Result returns bad request with details") {
                    patientUpdateResult.statusCode shouldBe HttpStatus.BAD_REQUEST
                    (patientUpdateResult.body as UpdatePatientResponseDto.Error)
                        .localizedMessage shouldContain "first name"
                }
            }

            When("Update patient with blank last name") {
                val patientUpdateResult = patientContext.updatePatient(patientId, PATIENT_UPDATE_MODEL_WITH_BLANK_LAST_NAME)
                Then("Result returns bad request with details") {
                    patientUpdateResult.statusCode shouldBe HttpStatus.BAD_REQUEST
                    (patientUpdateResult.body as UpdatePatientResponseDto.Error)
                        .localizedMessage shouldContain "last name"
                }
            }
        }



    }
})

private fun getPatientId(createPatient: ResponseEntity<CreatePatientResponseDto>): String {
    return (createPatient.body as CreatePatientResponseDto.Success).patientId
}
