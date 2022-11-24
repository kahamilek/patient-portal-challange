package pl.hanusek.patient.service.api.doctor

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.hanusek.patient.service.api.doctor.dto.*
import pl.hanusek.patient.service.api.patient.*
import pl.hanusek.patient.service.api.patient.dto.*
import pl.hanusek.patient.service.api.patient.toDomain
import pl.hanusek.patient.service.domain.OrderType
import pl.hanusek.patient.service.domain.doctor.Doctor
import pl.hanusek.patient.service.domain.doctor.DoctorInvalidArgumentException
import pl.hanusek.patient.service.domain.doctor.DoctorNotFoundException
import pl.hanusek.patient.service.domain.doctor.DoctorsFacade

@RestController
class DoctorsController(
    private val doctorsFacade: DoctorsFacade
) {


    @PostMapping("/api/v1/doctors")
    fun createDoctor(@RequestBody request: CreateDoctorRequestDto): ResponseEntity<CreateDoctorResponseDto> =
        kotlin.runCatching {
            val doctor = doctorsFacade.createDoctor(
                doctorFullName = request.fullName.toDomain(),
                doctorSpecializationName = request.specializationName.toDomainSpecialization(),
                organizationName = request.organizationName.toDomain()
            )
            ResponseEntity.ok(doctor.toDtoModel())
        }.getOrElse {
            if (it is DoctorInvalidArgumentException) {
                ResponseEntity.badRequest()
                    .body(CreateDoctorResponseDto.Error(localizedErrorMessage(it.errorType)))
            } else {
                logger.error(it) { "Error occurred while adding new doctor" }
                ResponseEntity.internalServerError()
                    .body(CreateDoctorResponseDto.Error(UNEXPECTED_ERROR_MESSAGE))
            }
        }

    @PutMapping("/api/v1/doctors/{doctorId}")
    fun updateDoctor(
        @PathVariable("doctorId") doctorId: String,
        @RequestBody updateDoctorRequest: UpdateDoctorRequestDto
    ): ResponseEntity<out UpdateDoctorResponseDto> = kotlin.runCatching {
        doctorsFacade.updateDoctor(
            doctorId = Doctor.DoctorId.from(doctorId),
            doctorWithNewData = updateDoctorRequest.toDomainModel()
        )
        ResponseEntity.ok(UpdateDoctorResponseDto.Success())
    }.getOrElse {
        when (it) {
            is DoctorInvalidArgumentException -> {
                ResponseEntity.badRequest()
                    .body(UpdateDoctorResponseDto.Error(localizedErrorMessage(it.errorType)))
            }
            is DoctorNotFoundException -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(UpdateDoctorResponseDto.Error("No doctor with specified id found"))
            else -> {
                logger.error(it) { "Error occurred while updating doctor with id $doctorId" }
                ResponseEntity.internalServerError()
                    .body(UpdateDoctorResponseDto.Error(UNEXPECTED_ERROR_MESSAGE))
            }
        }
    }

    @GetMapping("/api/v1/doctors")
    fun getDoctors(
        @RequestParam("page_number", required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam("page_size", required = false, defaultValue = "20") pageSize: Int,
        @RequestParam("order_type", required = false, defaultValue = OrderType.DEFAULT_ORDER_TYPE_TEXT) orderTypeText: String
    ): ResponseEntity<GetDoctorsResponseDto> = kotlin.runCatching {
        doctorsFacade.getDoctors(pageNumber, pageSize, OrderType.from(orderTypeText))
            .toDtoModel()
    }.getOrElse {
        logger.error(it) { "Unexpected error occurred" }
        ResponseEntity.internalServerError()
            .body(GetDoctorsResponseDto.Error(UNEXPECTED_ERROR_MESSAGE))
    }

    @DeleteMapping("/api/v1/doctors/{doctorId}")
    fun removeDoctor(@PathVariable doctorId: String): ResponseEntity<out RemoveDoctorResponseDto> = kotlin.runCatching {
        doctorsFacade.removeDoctor(Doctor.DoctorId.from(doctorId))
        ResponseEntity.ok(RemoveDoctorResponseDto.Success())
    }.getOrElse {
        logger.error(it) { "Unexpected error occurred" }
        ResponseEntity.internalServerError()
            .body(RemoveDoctorResponseDto.Error(UNEXPECTED_ERROR_MESSAGE))
    }

}


private const val UNEXPECTED_ERROR_MESSAGE = "Unexpected error occurred on server side. Please try later."
private val logger = KotlinLogging.logger { }

private fun localizedErrorMessage(errorType: DoctorInvalidArgumentException.ErrorType): String {
    return when (errorType) {
        DoctorInvalidArgumentException.ErrorType.FIRST_NAME_IS_BLANK -> "Missing first name"
        DoctorInvalidArgumentException.ErrorType.LAST_NAME_IS_BLANK -> "Missing last name"
    }
}
