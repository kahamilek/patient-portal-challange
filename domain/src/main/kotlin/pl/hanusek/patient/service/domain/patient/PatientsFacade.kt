package pl.hanusek.patient.service.domain.patient

import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.domain.OrderType
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.organization.Organization

interface PatientsFacade {

    fun createPatient(
        patientFullName: FullName,
        patientAddress: Patient.Address,
        organizationName: Organization.OrganizationName
    ): Patient

    @Throws(PatientNotFoundException::class)
    fun updatePatient(patientId: Patient.PatientId, patientWithNewData: PatientToUpdate)

    fun getPatients(pageNumber: Int, pageSize: Int, orderType: OrderType): SinglePage<PatientWithOrganizationName>

    @Throws(PatientNotFoundException::class)
    fun removePatient(patientId: Patient.PatientId)

    fun getPatient(patientId: Patient.PatientId): Patient?


    data class PatientToUpdate(
        val fullName: FullName,
        val address: Patient.Address,
    )

    data class PatientWithOrganizationName(
        val patient: Patient,
        val organizationName: Organization.OrganizationName
    )
}
