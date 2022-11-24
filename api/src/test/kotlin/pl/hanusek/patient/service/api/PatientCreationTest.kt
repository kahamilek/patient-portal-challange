package pl.hanusek.patient.service.api

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus
import pl.hanusek.patient.service.api.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.dto.CreatePatientResponseDto

class PatientCreationTest : BehaviorSpec({
    given("Initialized patient API context") {
        val patientContext = initializedEmptyPatientApiContext()

        When("Creating patient with valid model") {
            val patientCreationResult = patientContext.createPatient(VALID_PATIENT_MODEL)
            Then("Result is success with patient id") {
                patientCreationResult.statusCode shouldBe HttpStatus.OK
                (patientCreationResult.body as CreatePatientResponseDto.Success?)
                    .shouldNotBeNull()
                    .patientId.shouldNotBeNull()
            }

        }

        When("Creating patient with blank first name") {
            val patientCreationResult = patientContext.createPatient(PATIENT_MODEL_WITH_BLANK_FIRST_NAME)
            Then("Result returns bad request with details") {
                patientCreationResult.statusCode shouldBe HttpStatus.BAD_REQUEST
                (patientCreationResult.body as CreatePatientResponseDto.Error?)
                    .shouldNotBeNull()
                    .localizedMessage.shouldNotBeNull()
                    .contains("first name")
            }
        }

        When("Creating patient with blank last name") {
            val patientCreationResult = patientContext.createPatient(PATIENT_MODEL_WITH_BLANK_LAST_NAME)
            Then("Result returns bad request with details") {
                patientCreationResult.statusCode shouldBe HttpStatus.BAD_REQUEST
                (patientCreationResult.body as CreatePatientResponseDto.Error?)
                    .shouldNotBeNull()
                    .localizedMessage.shouldNotBeNull()
                    .contains("last name")
            }
        }
    }

}
)

fun initializedEmptyPatientApiContext(): PatientApiTestContext {
    return PatientApiTestContext()
}

private val VALID_PATIENT_MODEL = CreatePatientRequestDto(
    fullName = CreatePatientRequestDto.FullName(
        firstName = "John",
        lastName = "Smith"
    ),
    address = CreatePatientRequestDto.Address(
        city = "Poznań",
        postcode = "62-222",
        street = "Głogowska",
        buildingNumber = "3",
        apartmentNumber = null,
    ),
    organizationName = "Example",
)
private val PATIENT_MODEL_WITH_BLANK_FIRST_NAME = VALID_PATIENT_MODEL.copy(
    fullName = VALID_PATIENT_MODEL.fullName.copy(
        firstName = "       "
    )
)
private val PATIENT_MODEL_WITH_BLANK_LAST_NAME = VALID_PATIENT_MODEL.copy(
    fullName = VALID_PATIENT_MODEL.fullName.copy(
        lastName = "       "
    )
)
