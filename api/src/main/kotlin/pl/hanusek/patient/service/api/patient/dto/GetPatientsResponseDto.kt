package pl.hanusek.patient.service.api.patient.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "state"
)
sealed class GetPatientsResponseDto {

    @JsonTypeName("SUCCESS")
    data class Success(
        val patients: List<Patient>,
        val pageNumber: Int,
        val pageSize: Int,
        val totalNumberOfPages: Int
    ) : GetPatientsResponseDto()

    data class Patient(
        val fullName: CreatePatientRequestDto.FullName,
        val address: CreatePatientRequestDto.Address,
        val organizationName: String,
        val id: String
    )

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ) : GetPatientsResponseDto()
}
