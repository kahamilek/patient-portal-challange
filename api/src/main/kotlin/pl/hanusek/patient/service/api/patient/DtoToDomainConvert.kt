package pl.hanusek.patient.service.api.patient

import pl.hanusek.patient.service.api.patient.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.patient.dto.UpdatePatientRequestDto
import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.domain.organization.Organization
import pl.hanusek.patient.service.domain.patient.Patient
import pl.hanusek.patient.service.domain.patient.PatientsFacade

internal fun UpdatePatientRequestDto.toDomainModel(): PatientsFacade.PatientToUpdate {
    return PatientsFacade.PatientToUpdate(
        fullName = fullName.toDomain(),
        address = address.toDomain(),
    )
}

internal fun String.toDomain(): Organization.OrganizationName {
    return Organization.OrganizationName.from(this)
}

internal fun CreatePatientRequestDto.Address.toDomain(): Patient.Address {
    return Patient.Address(
        city = city,
        postcode = postcode,
        street = street,
        buildingNumber = buildingNumber,
        apartmentNumber = apartmentNumber,
    )
}

internal fun CreatePatientRequestDto.FullName.toDomain(): FullName {
    return FullName(
        firstName = firstName,
        lastName = lastName,
    )
}
