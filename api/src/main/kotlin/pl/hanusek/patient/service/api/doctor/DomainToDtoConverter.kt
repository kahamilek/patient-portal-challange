package pl.hanusek.patient.service.api.doctor

import org.springframework.http.ResponseEntity
import pl.hanusek.patient.service.api.doctor.dto.FullName
import pl.hanusek.patient.service.api.doctor.dto.GetDoctorsResponseDto
import pl.hanusek.patient.service.domain.FullName as FullNameDomain
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.doctor.Doctor
import pl.hanusek.patient.service.domain.doctor.DoctorsFacade

internal fun SinglePage<DoctorsFacade.DoctorWithOrganizationName>.toDtoModel(): ResponseEntity<GetDoctorsResponseDto> {
    return ResponseEntity.ok(
        GetDoctorsResponseDto.Success(
            doctors = elementsOnCurrentPage.map { it.toDtoModel() },
            pageNumber = pageNumber,
            pageSize = pageSize,
            totalNumberOfPages = totalNumberOfPages,
        )
    )
}

private fun DoctorsFacade.DoctorWithOrganizationName.toDtoModel(): GetDoctorsResponseDto.Doctor {
    return GetDoctorsResponseDto.Doctor(
        fullName = doctor.fullName.toDtoModel(),
        specializationName = doctor.specializationName.toDtoModel(),
        organizationName = organizationName.formattedName,
        id = doctor.id.value
    )
}

private fun Doctor.SpecializationName.toDtoModel(): String {
    return this.formattedName
}


private fun FullNameDomain.toDtoModel(): FullName {
    return FullName(
        firstName = firstName,
        lastName = lastName,
    )
}
