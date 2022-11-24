package pl.hanusek.patient.service.domain.patient

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.domain.OrderType
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.organization.Organization
import pl.hanusek.patient.service.domain.organization.OrganizationsFacade
import pl.hanusek.patient.service.domain.toDomainModel

@Component
internal class PatientsFacadeImpl(
    private val patientsRepository: PatientsRepository,
    private val organizationsFacade: OrganizationsFacade
) : PatientsFacade {

    @Transactional
    override fun createPatient(
        patientFullName: FullName,
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

    override fun updatePatient(patientId: Patient.PatientId, patientWithNewData: PatientsFacade.PatientToUpdate) {
        val patientToUpdate = patientsRepository.findById(patientId) ?: throw PatientNotFoundException(patientId)
        val updatedPatient = patientToUpdate.update(patientWithNewData)
        patientsRepository.updatePatient(updatedPatient)
    }

    override fun getPatients(
        pageNumber: Int,
        pageSize: Int,
        orderType: OrderType
    ): SinglePage<PatientsFacade.PatientWithOrganizationName> {
        val patientsOnPage = patientsRepository.getPatientsOnPage(pageNumber, pageSize, orderType)

        return patientsOnPage.map {
            PatientsFacade.PatientWithOrganizationName(
                it,
                organizationsFacade.getOrganizationNameById(it.organizationId)
            )
        }
            .toDomainModel()
    }

    override fun removePatient(patientId: Patient.PatientId) {
        val patientToRemove = patientsRepository.findById(patientId)
        return if (patientToRemove == null) {
            throw PatientNotFoundException(patientId)
        } else {
            patientsRepository.delete(patientToRemove)
        }
    }

    override fun getPatient(patientId: Patient.PatientId): Patient? {
        return patientsRepository.findById(patientId)
    }
}
