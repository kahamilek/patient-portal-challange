package pl.hanusek.patient.service.api.patient

import org.springframework.http.ResponseEntity
import pl.hanusek.patient.service.api.patient.dto.CreatePatientRequestDto
import pl.hanusek.patient.service.api.patient.dto.GetPatientsDtoResponse
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.patient.Patient
import pl.hanusek.patient.service.domain.patient.PatientsFacade

internal fun SinglePage<PatientsFacade.PatientWithOrganizationName>.toDtoModel(): ResponseEntity<GetPatientsDtoResponse> {
    return ResponseEntity.ok(
        GetPatientsDtoResponse.Success(
            patients = elementsOnCurrentPage.map { it.toDtoModel() },
            pageNumber = pageNumber,
            pageSize = pageSize,
            totalNumberOfPages = totalNumberOfPages,
        )
    )
}

private fun PatientsFacade.PatientWithOrganizationName.toDtoModel(): GetPatientsDtoResponse.Patient {
    return GetPatientsDtoResponse.Patient(
        fullName = patient.fullName.toDtoModel(),
        address = patient.address.toDtoModel(),
        organizationName = organizationName.formattedName,
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
