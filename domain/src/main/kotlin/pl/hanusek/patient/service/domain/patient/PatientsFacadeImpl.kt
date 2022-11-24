package pl.hanusek.patient.service.domain.patient

import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.hanusek.patient.service.domain.SinglePage
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

    override fun updatePatient(patientId: Patient.PatientId, patientWithNewData: PatientsFacade.PatientToUpdate) {
        val patientToUpdate = patientsRepository.findById(patientId) ?: throw PatientNotFoundException(patientId)
        val updatedPatient = patientToUpdate.update(patientWithNewData)
        patientsRepository.updatePatient(updatedPatient)
    }

    override fun getPatients(
        pageNumber: Int,
        pageSize: Int,
        orderType: PatientsFacade.OrderType
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
        val patientToRemove = patientsRepository.findById( patientId )
        return if(patientToRemove == null) {
            throw PatientNotFoundException(patientId)
        } else {
            patientsRepository.delete(patientToRemove)
        }
    }
}

private fun <T> Page<T>.toDomainModel(): SinglePage<T> {
    return SinglePage(
        pageNumber = this.pageable.pageNumber,
        pageSize = pageable.pageSize,
        totalNumberOfPages = totalPages,
        elementsOnCurrentPage = this.content,
    )
}
