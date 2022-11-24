package pl.hanusek.patient.service.domain.organization

import org.springframework.stereotype.Component

@Component
class OrganizationsFacadeImpl(
    private val organizationsRepository: OrganizationsRepository
) : OrganizationsFacade {

    override fun getOrCreateOrganizationIfNotExists(organizationName: Organization.OrganizationName): Organization {
        return organizationsRepository.getOrCreateIfNotExists(organizationName)
    }

    override fun getOrganizationNameById(organizationId: Organization.OrganizationId): Organization.OrganizationName {
        return organizationsRepository.findById(organizationId.value)!!
            .name

    }
}
