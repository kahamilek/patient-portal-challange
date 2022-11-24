package pl.hanusek.patient.service.domain.patient

import pl.hanusek.patient.service.domain.organization.OrganizationsFacade
import pl.hanusek.patient.service.domain.organization.OrganizationsFacadeImpl
import pl.hanusek.patient.service.domain.organization.OrganizationsInMemoryRepository
import pl.hanusek.patient.service.domain.organization.OrganizationsRepository

class PatientTestContext {

    private val organizationsInMemoryRepository: OrganizationsRepository = OrganizationsInMemoryRepository()
    private val organizationsFacade: OrganizationsFacade = OrganizationsFacadeImpl(
        organizationsRepository = organizationsInMemoryRepository
    )
    private val patientsInMemoryRepository: PatientsRepository = PatientsInMemoryRepository()
    val patientsFacade: PatientsFacade = PatientsFacadeImpl(
        patientsRepository = patientsInMemoryRepository,
        organizationsFacade = organizationsFacade
    )
}
