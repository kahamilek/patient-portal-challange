package pl.hanusek.patient.service.domain.patient

import pl.hanusek.patient.service.domain.organization.Organization

interface PatientsFacade {

    fun createPatient(
        patientFullName: Patient.FullName,
        patientAddress: Patient.Address,
        organizationName: Organization.OrganizationName
    ): Patient

    @Throws(PatientNotFoundException::class)
    fun updatePatient(patientId: Patient.PatientId, patientWithNewData: PatientToUpdate)

    data class PatientToUpdate(
        val fullName: Patient.FullName,
        val address: Patient.Address,
    )
}
