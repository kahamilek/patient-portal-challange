package pl.hanusek.patient.service.api.doctor

import pl.hanusek.patient.service.api.doctor.dto.CreateDoctorResponseDto
import pl.hanusek.patient.service.api.doctor.dto.FullName
import pl.hanusek.patient.service.api.doctor.dto.UpdateDoctorRequestDto
import pl.hanusek.patient.service.domain.FullName as FullNameDomain
import pl.hanusek.patient.service.domain.doctor.Doctor
import pl.hanusek.patient.service.domain.doctor.DoctorsFacade
import pl.hanusek.patient.service.domain.organization.Organization

internal fun UpdateDoctorRequestDto.toDomainModel(): DoctorsFacade.DoctorToUpdate {
    return DoctorsFacade.DoctorToUpdate(
        fullName = fullName.toDomain(),
        specializationName = specializationName.toDomainSpecialization(),
    )
}

internal fun String.toDomainSpecialization(): Doctor.SpecializationName {
    return Doctor.SpecializationName.from(this)
}


internal fun Doctor.toDtoModel(): CreateDoctorResponseDto {
    return CreateDoctorResponseDto.Success(this.id.value)
}

internal fun FullName.toDomain(): FullNameDomain {
    return FullNameDomain(
        firstName = firstName,
        lastName = lastName,
    )
}
