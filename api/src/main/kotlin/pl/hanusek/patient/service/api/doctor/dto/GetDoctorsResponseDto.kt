package pl.hanusek.patient.service.api.doctor.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "state"
)
sealed class GetDoctorsResponseDto {

    @JsonTypeName("SUCCESS")
    data class Success(
        val doctors: List<Doctor>,
        val pageNumber: Int,
        val pageSize: Int,
        val totalNumberOfPages: Int
    ) : GetDoctorsResponseDto()

    data class Doctor(
        val fullName: FullName,
        val specializationName: String,
        val organizationName: String,
        val id: String
    )

    @JsonTypeName("ERROR")
    data class Error(
        val localizedMessage: String
    ) : GetDoctorsResponseDto()
}
