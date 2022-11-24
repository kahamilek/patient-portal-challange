package pl.hanusek.patient.service.api.patient

import org.springframework.http.ResponseEntity
import pl.hanusek.patient.service.api.patient.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.patient.dto.GetPatientsResponseDto
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.patient.Patient
import pl.hanusek.patient.service.domain.patient.PatientsFacade

internal fun SinglePage<PatientsFacade.PatientWithOrganizationName>.toDtoModel(): ResponseEntity<GetPatientsResponseDto> {
    return ResponseEntity.ok(
        GetPatientsResponseDto.Success(
            patients = elementsOnCurrentPage.map { it.toDtoModel() },
            pageNumber = pageNumber,
            pageSize = pageSize,
            totalNumberOfPages = totalNumberOfPages,
        )
    )
}

private fun PatientsFacade.PatientWithOrganizationName.toDtoModel(): GetPatientsResponseDto.Patient {
    return GetPatientsResponseDto.Patient(
        fullName = patient.fullName.toDtoModel(),
        address = patient.address.toDtoModel(),
        organizationName = organizationName.formattedName,
        id = patient.id.value
    )
}

private fun Patient.Address.toDtoModel(): CreatePatientRequestDto.Address {
    return CreatePatientRequestDto.Address(
        city = city,
        postcode = postcode,
        street = street,
        buildingNumber = buildingNumber,
        apartmentNumber = apartmentNumber,
    )
}

private fun Patient.FullName.toDtoModel(): CreatePatientRequestDto.FullName {
    return CreatePatientRequestDto.FullName(
        firstName = firstName,
        lastName = lastName,
    )
}
