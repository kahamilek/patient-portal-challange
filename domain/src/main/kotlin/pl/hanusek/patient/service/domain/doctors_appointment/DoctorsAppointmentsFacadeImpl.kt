package pl.hanusek.patient.service.domain.doctors_appointment

import org.springframework.stereotype.Component
import pl.hanusek.patient.service.domain.Pageable
import pl.hanusek.patient.service.domain.SinglePage
import pl.hanusek.patient.service.domain.doctor.Doctor
import pl.hanusek.patient.service.domain.doctor.DoctorsFacade
import pl.hanusek.patient.service.domain.patient.Patient
import pl.hanusek.patient.service.domain.patient.PatientsFacade
import pl.hanusek.patient.service.domain.toDomainModel
import java.time.LocalDate
import java.time.LocalTime

@Component
internal class DoctorsAppointmentsFacadeImpl(
    private val doctorsFacade: DoctorsFacade,
    private val patientsFacade: PatientsFacade,
    private val doctorsAppointmentsRepository: DoctorsAppointmentsRepository
) : DoctorsAppointmentsFacade {

    override fun createDoctorsAppointment(
        date: LocalDate,
        time: LocalTime,
        place: DoctorsAppointment.Place,
        attendingPhysicianId: Doctor.DoctorId,
        patientId: Patient.PatientId
    ): DoctorsAppointmentsFacade.EnrichedDoctorsAppointment {
        val attendingPhysician =
            doctorsFacade.getDoctor(attendingPhysicianId) ?: throw DoctorsAppointmentInvalidArgumentException.noDoctor()
        val patient =
            patientsFacade.getPatient(patientId) ?: throw DoctorsAppointmentInvalidArgumentException.noPatient()
        val doctorsAppointments = DoctorsAppointment(
            date = date,
            time = time,
            place = place,
            attendingPhysician = attendingPhysician,
            patient = patient,
        )

        return doctorsAppointmentsRepository.addNewDoctorsAppointment(doctorsAppointments)
            .enrich(patient, attendingPhysician)
    }

    override fun removeDoctorsAppointment(id: DoctorsAppointment.DoctorsAppointmentId) {
        val doctorsAppointment =
            doctorsAppointmentsRepository.findDoctorAppointmentById(id) ?: throw DoctorsAppointmentNotFoundException(id)
        doctorsAppointmentsRepository.removeDoctorAppointment(doctorsAppointment)
    }

    override fun changeTimeOfDoctorsAppointment(
        id: DoctorsAppointment.DoctorsAppointmentId,
        newTime: LocalTime
    ): DoctorsAppointmentsFacade.EnrichedDoctorsAppointment {
        val doctorsAppointment =
            doctorsAppointmentsRepository.findDoctorAppointmentById(id) ?: throw DoctorsAppointmentNotFoundException(id)
        val doctorsAppointmentWithNewTime = doctorsAppointment.copy(time = newTime)
        return doctorsAppointmentsRepository.updateDoctorsAppointment(doctorsAppointmentWithNewTime)
            .enrich(
                patient = patientsFacade.getPatient(doctorsAppointmentWithNewTime.patientId)!!,
                doctor = doctorsFacade.getDoctor(doctorsAppointmentWithNewTime.attendingPhysicianId)!!
            )
    }

    override fun getAllDoctorsAppointments(
        patientId: Patient.PatientId?,
        pageable: Pageable
    ): SinglePage<DoctorsAppointmentsFacade.EnrichedDoctorsAppointment> {
        val pageOfEnrichedDoctorsAppointments = doctorsAppointmentsRepository.getAllDoctorsAppointment(patientId, pageable)
            .map { doctorsAppointment ->
                val patientForAppointment = patientsFacade.getPatient(doctorsAppointment.patientId)!!
                val doctorForAppointment = doctorsFacade.getDoctor(doctorsAppointment.attendingPhysicianId)!!
                doctorsAppointment.enrich(patientForAppointment, doctorForAppointment)
            }
            .toDomainModel()

        return pageOfEnrichedDoctorsAppointments
    }
}

private fun DoctorsAppointment.enrich(
    patient: Patient,
    doctor: Doctor
): DoctorsAppointmentsFacade.EnrichedDoctorsAppointment {
    return DoctorsAppointmentsFacade.EnrichedDoctorsAppointment(
        doctorsAppointment = this,
        patientsFullName = patient.fullName,
        doctorsFullName = doctor.fullName
    )
}
