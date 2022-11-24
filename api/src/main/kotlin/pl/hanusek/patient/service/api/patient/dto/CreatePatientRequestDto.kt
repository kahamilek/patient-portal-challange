package pl.hanusek.patient.service.api.patient.dto

data class CreatePatientRequestDto(
    val fullName: FullName,
    val address: Address,
    val organizationName: String
) {

    data class FullName(
        val firstName: String,
        val lastName: String
    )

    data class Address(
        val city: String,
        val postcode: String,
        val street: String?,
        val buildingNumber: String,
        val apartmentNumber: String?
    )
}
