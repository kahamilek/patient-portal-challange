package pl.hanusek.patient.service.domain.doctors_appointment

import org.springframework.data.domain.Page
import pl.hanusek.patient.service.domain.Pageable
import pl.hanusek.patient.service.domain.patient.Patient

interface DoctorsAppointmentsRepository {

    fun addNewDoctorsAppointment(doctorsAppointment: DoctorsAppointment): DoctorsAppointment

    fun updateDoctorsAppointment(doctorsAppointment: DoctorsAppointment): DoctorsAppointment

    fun removeDoctorAppointment(doctorsAppointment: DoctorsAppointment)

    fun findDoctorAppointmentById(doctorsAppointmentId: DoctorsAppointment.DoctorsAppointmentId): DoctorsAppointment?

    fun getAllDoctorsAppointment(patientId: Patient.PatientId?, pageable: Pageable): Page<DoctorsAppointment>
}
