package pl.hanusek.patient.service.domain.organization

interface OrganizationsFacade {

    fun getOrCreateOrganizationIfNotExists(organizationName: Organization.OrganizationName): Organization
    fun getOrganizationNameById(organizationId: Organization.OrganizationId): Organization.OrganizationName
}
