package pl.hanusek.patient.service.domain.patient

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.hanusek.patient.service.domain.organization.Organization
import pl.hanusek.patient.service.domain.organization.OrganizationsFacade

@Component
internal class PatientsFacadeImpl(
    private val patientsRepository: PatientsRepository,
    private val organizationsFacade: OrganizationsFacade
) : PatientsFacade {

    @Transactional
    override fun createPatient(
        patientFullName: Patient.FullName,
        patientAddress: Patient.Address,
        organizationName: Organization.OrganizationName
    ): Patient {
        val patientOrganizationId = organizationsFacade.getOrCreateOrganizationIfNotExists(organizationName)
            .id
        return patientsRepository.save(
            Patient(
                fullName = patientFullName,
                address = patientAddress,
                organizationId = patientOrganizationId
            )
        )
    }
}
