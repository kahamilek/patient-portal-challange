package pl.hanusek.patient.service.domain.doctor

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.domain.OrderType
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.organization.Organization
import pl.hanusek.patient.service.domain.organization.OrganizationsFacade
import pl.hanusek.patient.service.domain.toDomainModel


@Component
internal class DoctorsFacadeImpl(
    private val doctorsRepository: DoctorsRepository,
    private val organizationsFacade: OrganizationsFacade
) : DoctorsFacade {

    @Transactional
    override fun createDoctor(
        doctorFullName: FullName,
        doctorSpecialization: Doctor.SpecializationName,
        organizationName: Organization.OrganizationName
    ): Doctor {
        val patientOrganizationId = organizationsFacade.getOrCreateOrganizationIfNotExists(organizationName)
            .id
        return doctorsRepository.save(
            Doctor(
                fullName = doctorFullName,
                specializationName = doctorSpecialization,
                organizationId = patientOrganizationId
            )
        )
    }

    override fun updateDoctor(doctorId: Doctor.DoctorId, doctorWithNewData: DoctorsFacade.DoctorToUpdate) {
        val doctorToUpdate = doctorsRepository.findById(doctorId) ?: throw DoctorNotFoundException(doctorId)
        val updatedPatient = doctorToUpdate.update(doctorWithNewData)
        doctorsRepository.updateDoctor(updatedPatient)
    }

    override fun getDoctors(
        pageNumber: Int,
        pageSize: Int,
        orderType: OrderType
    ): SinglePage<DoctorsFacade.DoctorWithOrganizationName> {
        val doctorsOnPage = doctorsRepository.getDoctorsOnPage(pageNumber, pageSize, orderType)

        return doctorsOnPage.map {
            DoctorsFacade.DoctorWithOrganizationName(
                it,
                organizationsFacade.getOrganizationNameById(it.organizationId)
            )
        }
            .toDomainModel()
    }

    override fun removeDoctor(doctorId: Doctor.DoctorId) {
        val doctorToRemove = doctorsRepository.findById(doctorId)
        return if (doctorToRemove == null) {
            throw DoctorNotFoundException(doctorId)
        } else {
            doctorsRepository.delete(doctorToRemove)
        }
    }
}
