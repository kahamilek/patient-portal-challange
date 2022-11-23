package pl.hanusek.patient.service.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pl.hanusek.patient.service.domain.organization.Organization
import pl.hanusek.patient.service.domain.organization.OrganizationsRepository

@Repository
internal class OrganizationsDbRepository(
    private val organizationsJpaRepository: OrganizationsJpaRepository
) : OrganizationsRepository {

    override fun getOrCreateIfNotExists(organizationName: Organization.OrganizationName): Organization {
        val newOrganization = Organization(organizationName)
        return organizationsJpaRepository.insertOrGetAlreadyCreated(
            newOrganization.id.value,
            newOrganization.name.formattedName
        )
    }


}

internal interface OrganizationsJpaRepository : JpaRepository<Organization, String> {
    @Query(
        nativeQuery = true,
        value = """
            INSERT INTO ${Organization.TABLE_NAME} (id, name)
            VALUES (:id, :name)
            ON CONFLICT DO NOTHING
            RETURNING *
        """
    )
    fun insertOrGetAlreadyCreated(id: String, name: String): Organization
}
