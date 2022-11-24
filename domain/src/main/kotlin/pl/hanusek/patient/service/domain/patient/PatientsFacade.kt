package pl.hanusek.patient.service.domain.patient

import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.organization.Organization

interface PatientsFacade {

    fun createPatient(
        patientFullName: Patient.FullName,
        patientAddress: Patient.Address,
        organizationName: Organization.OrganizationName
    ): Patient

    @Throws(PatientNotFoundException::class)
    fun updatePatient(patientId: Patient.PatientId, patientWithNewData: PatientToUpdate)

    fun getPatients(pageNumber: Int, pageSize: Int, orderType: OrderType): SinglePage<PatientWithOrganizationName>

    @Throws(PatientNotFoundException::class)
    fun removePatient(patientId: Patient.PatientId)


    enum class OrderType {
        ASC,
        DESC;

        companion object {
            const val DEFAULT_ORDER_TYPE_TEXT = "ASC"

            fun from(text: String): OrderType {
                return OrderType.values()
                    .firstOrNull { it.toString() == text }
                    ?: ASC
            }
        }
    }


    data class PatientToUpdate(
        val fullName: Patient.FullName,
        val address: Patient.Address,
    )

    data class PatientWithOrganizationName(
        val patient: Patient,
        val organizationName: Organization.OrganizationName
    )
}
