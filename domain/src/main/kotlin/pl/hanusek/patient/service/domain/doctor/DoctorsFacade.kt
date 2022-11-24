package pl.hanusek.patient.service.domain.doctor

import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.domain.OrderType
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.organization.Organization

interface DoctorsFacade {

    fun createDoctor(
        doctorFullName: FullName,
        doctorSpecializationName: Doctor.SpecializationName,
        organizationName: Organization.OrganizationName
    ): Doctor

    @Throws(DoctorNotFoundException::class)
    fun updateDoctor(doctorId: Doctor.DoctorId, doctorWithNewData: DoctorToUpdate)

    fun getDoctors(
        pageNumber: Int,
        pageSize: Int,
        orderType: OrderType
    ): SinglePage<DoctorWithOrganizationName>

    @Throws(DoctorNotFoundException::class)
    fun removeDoctor(doctorId: Doctor.DoctorId)

    fun getDoctor(attendingPhysicianId: Doctor.DoctorId): Doctor?

    data class DoctorWithOrganizationName(
        val doctor: Doctor,
        val organizationName: Organization.OrganizationName
    )

    data class DoctorToUpdate(
        val fullName: FullName,
        val specializationName: Doctor.SpecializationName,
    )

    companion object
}
