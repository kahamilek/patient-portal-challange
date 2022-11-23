package pl.hanusek.patient.service.api

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus
import pl.hanusek.patient.service.api.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.dto.CreatePatientResponseDto

class PatientCreationTest : BehaviorSpec({
    given("Initialized empty context") {
        val context = initializedEmptyContext()
        When("Creating patient with valid model") {
            val patientCreationResult = context.createPatient(VALID_PATIENT_MODEL)
            Then("Result is success with patient id") {
                patientCreationResult.statusCode shouldBe HttpStatus.OK
                (patientCreationResult.body as CreatePatientResponseDto.Success?)
                    .shouldNotBeNull()
                    .patientId.shouldNotBeNull()
            }

        }
    }

}
)

fun initializedEmptyContext(): PatientApiTestContext {
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
