package pl.hanusek.patient.service.api.doctor_appointment

import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.hanusek.patient.service.api.doctor_appointment.dto.CreateDoctorsAppointmentRequestDto
import pl.hanusek.patient.service.api.doctor_appointment.dto.CreateDoctorsAppointmentResponseDto
import pl.hanusek.patient.service.api.doctor_appointment.dto.DoctorsAppointmentDto
import pl.hanusek.patient.service.api.doctor_appointment.dto.GetDoctorsAppointmentsResponseDto
import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.domain.OrderType
import pl.hanusek.patient.service.domain.Pageable
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.doctor.Doctor
import pl.hanusek.patient.service.domain.doctors_appointment.DoctorsAppointment
import pl.hanusek.patient.service.domain.doctors_appointment.DoctorsAppointmentsFacade
import pl.hanusek.patient.service.domain.patient.Patient
import java.time.LocalTime

@RestController
class DoctorsAppointmentRestController(
    private val doctorsAppointmentsFacade: DoctorsAppointmentsFacade
) {

    @PostMapping("/api/v1/doctors-appointment")
    fun createDoctorsAppointment(@RequestBody requestDto: CreateDoctorsAppointmentRequestDto): ResponseEntity<CreateDoctorsAppointmentResponseDto> =
        kotlin.runCatching {
            ResponseEntity.ok(successResult(doctorsAppointmentsFacade.createDoctorsAppointment(
                date = requestDto.date,
                time = requestDto.time,
                place = DoctorsAppointment.Place(requestDto.place),
                attendingPhysicianId = Doctor.DoctorId.from(requestDto.attendingPhysicianId),
                patientId = Patient.PatientId.from(requestDto.patientId),
            ).toDtoModel()))
        }.getOrElse {
            logger.error(it) {}
            TODO("No time") }

    @DeleteMapping("/api/v1/doctors-appointment/{appointmentId}")
    fun removeDoctorsAppointment(@PathVariable appointmentId: DoctorsAppointment.DoctorsAppointmentId): ResponseEntity<String> =
        kotlin.runCatching {
            doctorsAppointmentsFacade.removeDoctorsAppointment(appointmentId)
            ResponseEntity.ok("No time")
        }.getOrElse { ResponseEntity.internalServerError().body("Error") }

    @PutMapping("/api/v1/doctors-appointment/{appointmentId}")
    fun changeTimeOfAppointment(
        @PathVariable appointmentId: String,
        newTime: LocalTime
    ): DoctorsAppointmentDto {
        return doctorsAppointmentsFacade.changeTimeOfDoctorsAppointment(
            DoctorsAppointment.DoctorsAppointmentId.from(
                appointmentId
            ), newTime
        )
            .toDtoModel()
    }

    @GetMapping("/api/v1/doctors-appointment")
    fun getDoctorsAppointments(
        @RequestParam("patient_id") patientId: String?,
        @RequestParam("page_number", required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam("page_size", required = false, defaultValue = "20") pageSize: Int,
        @RequestParam("order_type", required = false, defaultValue = OrderType.DEFAULT_ORDER_TYPE_TEXT) orderTypeText: String

    ): GetDoctorsAppointmentsResponseDto = kotlin.runCatching {
        doctorsAppointmentsFacade.getAllDoctorsAppointments(
            patientId = patientId?.let { Patient.PatientId.from(it) },
            pageable = Pageable(
                pageSize = pageSize,
                    pageNumber = pageNumber,
                    orderType = OrderType.from(orderTypeText),
            )
        ).toDomainModel()
    }.getOrElse { GetDoctorsAppointmentsResponseDto.Error("No time") }
}
private val logger = KotlinLogging.logger { }

private fun  SinglePage<DoctorsAppointmentsFacade.EnrichedDoctorsAppointment>.toDomainModel(): GetDoctorsAppointmentsResponseDto.Success {
    return GetDoctorsAppointmentsResponseDto.Success(
        doctorsAppointments = elementsOnCurrentPage.map { it.toDtoModel() },
        pageNumber = pageNumber,
        pageSize = pageSize,
        totalNumberOfPages = totalNumberOfPages
    )
}

private fun DoctorsAppointmentsFacade.EnrichedDoctorsAppointment.toDtoModel(): DoctorsAppointmentDto {
    return DoctorsAppointmentDto(
        id = this.doctorsAppointment.id.value,
        date = this.doctorsAppointment.date,
        time = doctorsAppointment.time,
        place = doctorsAppointment.place.value,
        patientFullName = patientsFullName.toDtoModel(),
        doctorFullName = doctorsFullName.toDtoModel()
    )
}

private fun FullName.toDtoModel(): pl.hanusek.patient.service.api.FullName {
    return pl.hanusek.patient.service.api.FullName(
        firstName = firstName,
        lastName = lastName,
    )
}
private fun successResult(doctorsAppointmentDto: DoctorsAppointmentDto): CreateDoctorsAppointmentResponseDto = CreateDoctorsAppointmentResponseDto.Success(doctorsAppointmentDto)
