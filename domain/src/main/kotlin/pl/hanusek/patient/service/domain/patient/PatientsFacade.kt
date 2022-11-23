package pl.hanusek.patient.service.domain.patient

import pl.hanusek.patient.service.domain.organization.Organization

interface PatientsFacade {

    fun createPatient(
        patientFullName: Patient.FullName,
        patientAddress: Patient.Address,
        organizationName: Organization.OrganizationName
    ): Patient
}
