package pl.hanusek.patient.service.domain.organization

import pl.hanusek.patient.service.domain.organization.Organization.Companion.TABLE_NAME
import java.util.*
import javax.persistence.*

@Entity
@Table(name = TABLE_NAME)
data class Organization private constructor(
    @Id
    val id: OrganizationId,
    val name: OrganizationName
) {

    constructor(organizationName: OrganizationName) : this(
        name = organizationName,
        id = OrganizationId(UUID.randomUUID().toString())
    )

    companion object {
        const val TABLE_NAME = "organization"
    }

    @JvmInline
    value class OrganizationId(
        val value: String
    )

    @JvmInline
    value class OrganizationName private constructor(
        val formattedName: String
    ) {

        companion object {
            fun from(organizationName: String) = OrganizationName(
                normalize(organizationName)
            )

            private fun normalize(organizationName: String): String {
                return organizationName
                    .replace("[., ]", "_")
                    .trim()
            }
        }


    }
}
