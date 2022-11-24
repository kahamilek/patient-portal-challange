package pl.hanusek.patient.service.api.fixtures

import pl.hanusek.patient.service.api.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.dto.UpdatePatientRequestDto

internal val VALID_PATIENT_CREATION_MODEL = CreatePatientRequestDto(
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
internal val PATIENT_CREATION_MODEL_WITH_BLANK_FIRST_NAME = VALID_PATIENT_CREATION_MODEL.copy(
    fullName = VALID_PATIENT_CREATION_MODEL.fullName.copy(
        firstName = "       "
    )
)
internal val PATIENT_CREATION_MODEL_WITH_BLANK_LAST_NAME = VALID_PATIENT_CREATION_MODEL.copy(
    fullName = VALID_PATIENT_CREATION_MODEL.fullName.copy(
        lastName = "       "
    )
)

internal val VALID_PATIENT_UPDATE_MODEL = UpdatePatientRequestDto(
    fullName = VALID_PATIENT_CREATION_MODEL.fullName,
    address = VALID_PATIENT_CREATION_MODEL.address
)

internal val PATIENT_UPDATE_MODEL_WITH_BLANK_FIRST_NAME = UpdatePatientRequestDto(
    fullName = PATIENT_CREATION_MODEL_WITH_BLANK_FIRST_NAME.fullName,
    address = VALID_PATIENT_CREATION_MODEL.address
)
internal val PATIENT_UPDATE_MODEL_WITH_BLANK_LAST_NAME = UpdatePatientRequestDto(
    fullName = PATIENT_CREATION_MODEL_WITH_BLANK_LAST_NAME.fullName,
    address = VALID_PATIENT_CREATION_MODEL.address
)

internal const val INVALID_PATIENT_ID = "patient_id"
