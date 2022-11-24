package pl.hanusek.patient.service.domain.organization

import java.util.concurrent.ConcurrentHashMap

class OrganizationsInMemoryRepository : OrganizationsRepository {

    private val organizationByOrganizationName = ConcurrentHashMap<Organization.OrganizationName, Organization>()

    override fun getOrCreateIfNotExists(organizationName: Organization.OrganizationName): Organization {
        return organizationByOrganizationName.putIfAbsent(organizationName, Organization(organizationName))
            ?: organizationByOrganizationName.getValue(organizationName)
    }

    override fun findById(value: String): Organization? {
        return organizationByOrganizationName.values
            .firstOrNull { it.id.value == value }
    }
}
