package pl.hanusek.patient.service.domain.doctor

import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.domain.organization.Organization
import pl.hanusek.patient.service.domain.patient.Patient
import java.time.Instant
import java.util.*
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "doctor")
data class Doctor private constructor(
    @Id
    val id: DoctorId,
    @Convert(converter = FullNameAttributeConverter::class)
    val fullName: FullName,
    val specializationName: SpecializationName,
    val creationTimestamp: Instant,
    val organizationId: Organization.OrganizationId
) {
    constructor(
        fullName: FullName,
        specializationName: SpecializationName,
        organizationId: Organization.OrganizationId
    ) : this(
        id = DoctorId(UUID.randomUUID().toString()),
        fullName = fullName,
        specializationName = specializationName,
        organizationId = organizationId,
        creationTimestamp = Instant.now()
    )

    fun update(doctorWithNewData: DoctorsFacade.DoctorToUpdate): Doctor {
        return this.copy(
            fullName = doctorWithNewData.fullName,
            specializationName = doctorWithNewData.specializationName
        )
    }

    @JvmInline
    value class SpecializationName private constructor(
        val formattedName: String
    ) {
        companion object {
            fun from(value: String) = SpecializationName(
                value.trim()
                    .uppercase()
            )
        }
    }
    @JvmInline
    value class DoctorId(
        val value: String
    ) {
        companion object {
            fun from(patientId: String): DoctorId {
                return DoctorId(patientId)
            }
        }
    }
}
