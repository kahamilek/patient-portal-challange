package pl.hanusek.patient.service.api

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.springframework.http.HttpStatus
import pl.hanusek.patient.service.api.patient.dto.CreatePatientResponseDto
import pl.hanusek.patient.service.api.fixtures.PATIENT_CREATION_MODEL_WITH_BLANK_FIRST_NAME
import pl.hanusek.patient.service.api.fixtures.PATIENT_CREATION_MODEL_WITH_BLANK_LAST_NAME
import pl.hanusek.patient.service.api.fixtures.VALID_PATIENT_CREATION_MODEL

class PatientCreationTest : BehaviorSpec({
    given("Initialized patient API context") {
        val patientContext = initializedEmptyPatientApiContext()

        When("Creating patient with valid model") {
            val patientCreationResult = patientContext.createPatient(VALID_PATIENT_CREATION_MODEL)
            Then("Result is success with patient id") {
                patientCreationResult.statusCode shouldBe HttpStatus.OK
                (patientCreationResult.body as CreatePatientResponseDto.Success?)
                    .shouldNotBeNull()
                    .patientId.shouldNotBeNull()
            }

        }

        When("Creating patient with blank first name") {
            val patientCreationResult = patientContext.createPatient(PATIENT_CREATION_MODEL_WITH_BLANK_FIRST_NAME)
            Then("Result returns bad request with details") {
                patientCreationResult.statusCode shouldBe HttpStatus.BAD_REQUEST
                (patientCreationResult.body as CreatePatientResponseDto.Error?)
                    .shouldNotBeNull()
                    .localizedMessage.shouldNotBeNull() shouldContain "first name"
            }
        }

        When("Creating patient with blank last name") {
            val patientCreationResult = patientContext.createPatient(PATIENT_CREATION_MODEL_WITH_BLANK_LAST_NAME)
            Then("Result returns bad request with details") {
                patientCreationResult.statusCode shouldBe HttpStatus.BAD_REQUEST
                (patientCreationResult.body as CreatePatientResponseDto.Error?)
                    .shouldNotBeNull()
                    .localizedMessage.shouldNotBeNull() shouldContain "last name"
            }
        }
    }

}
)
