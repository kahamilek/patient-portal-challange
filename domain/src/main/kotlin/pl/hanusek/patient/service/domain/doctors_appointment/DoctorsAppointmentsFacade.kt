package pl.hanusek.patient.service.domain.doctors_appointment

import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.domain.Pageable
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.doctor.Doctor
import pl.hanusek.patient.service.domain.patient.Patient
import java.time.LocalDate
import java.time.LocalTime

interface DoctorsAppointmentsFacade {

    @Throws(DoctorsAppointmentInvalidArgumentException::class)
    fun createDoctorsAppointment(
        date: LocalDate,
        time: LocalTime,
        place: DoctorsAppointment.Place,
        attendingPhysicianId: Doctor.DoctorId,
        patientId: Patient.PatientId
    ): EnrichedDoctorsAppointment

    @Throws(DoctorsAppointmentNotFoundException::class)
    fun removeDoctorsAppointment(id: DoctorsAppointment.DoctorsAppointmentId)

    fun changeTimeOfDoctorsAppointment(
        id: DoctorsAppointment.DoctorsAppointmentId,
        newTime: LocalTime
    ): EnrichedDoctorsAppointment

    fun getAllDoctorsAppointments(patientId: Patient.PatientId?, pageable: Pageable): SinglePage<EnrichedDoctorsAppointment>

    data class EnrichedDoctorsAppointment(
        val doctorsAppointment: DoctorsAppointment,
        val patientsFullName: FullName,
        val doctorsFullName: FullName
    )
}
