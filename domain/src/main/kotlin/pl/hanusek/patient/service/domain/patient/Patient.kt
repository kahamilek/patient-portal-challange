package pl.hanusek.patient.service.domain.patient

import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.domain.organization.Organization
import java.time.Instant
import java.util.*
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "patient")
data class Patient private constructor(
    @Id
    val id: PatientId,
    @Convert(converter = FullNameAttributeConverter::class)
    val fullName: FullName,
    @Convert(converter = AddressAttributeConverter::class)
    val address: Address,
    val creationTimestamp: Instant,
    val organizationId: Organization.OrganizationId
) {
    constructor(
        fullName: FullName,
        address: Address,
        organizationId: Organization.OrganizationId
    ) : this(
        id = PatientId(UUID.randomUUID().toString()),
        fullName = fullName,
        address = address,
        organizationId = organizationId,
        creationTimestamp = Instant.now()
    )

    fun update(patientWithNewData: PatientsFacade.PatientToUpdate): Patient {
        return this.copy(
            fullName = patientWithNewData.fullName,
            address = patientWithNewData.address
        )
    }

    @JvmInline
    value class PatientId(
        val value: String
    ) {
        companion object {
            fun from(patientId: String): PatientId {
                return PatientId(patientId)
            }
        }
    }


    data class Address(
        val city: String,
        val postcode: String,
        val street: String?,
        val buildingNumber: String,
        val apartmentNumber: String?
    )
}
