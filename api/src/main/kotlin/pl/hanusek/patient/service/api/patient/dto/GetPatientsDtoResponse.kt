package pl.hanusek.patient.service.api.patient.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "state"
)
sealed class GetPatientsDtoResponse {

    @JsonTypeName("SUCCESS")
    data class Success(
        val patients: List<Patient>,
        val pageNumber: Int,
        val pageSize: Int,
        val totalNumberOfPages: Int
    ) : GetPatientsDtoResponse()

    data class Patient(
        val fullName: CreatePatientRequestDto.FullName,
        val address: CreatePatientRequestDto.Address,
        val organizationName: String
    )

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ) : GetPatientsDtoResponse()
}
