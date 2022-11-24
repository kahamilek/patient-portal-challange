package pl.hanusek.patient.service.domain.doctors_appointment

import pl.hanusek.patient.service.domain.doctor.Doctor
import pl.hanusek.patient.service.domain.patient.Patient
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "doctors_appointment")
data class DoctorsAppointment private constructor(
    @Id
    val id: DoctorsAppointmentId,
    val creationTimestamp: Instant,
    val date: LocalDate,
    val time: LocalTime,
    val place: Place,
    val attendingPhysicianId: Doctor.DoctorId,
    val patientId: Patient.PatientId
) {

    constructor(
        date: LocalDate,
        time: LocalTime,
        place: Place,
        attendingPhysician: Doctor,
        patient: Patient
    ) : this(
        id = DoctorsAppointmentId(UUID.randomUUID().toString()),
        creationTimestamp = Instant.now(),
        date = date,
        time = time,
        place = place,
        attendingPhysicianId = attendingPhysician.id,
        patientId = patient.id,
    ) {
        if (attendingPhysician.organizationId != patient.organizationId) throw DoctorsAppointmentInvalidArgumentException.differentOrganizations()
    }

    @JvmInline
    value class DoctorsAppointmentId(
        val value: String
    ) {
        companion object {
            fun from(id: String): DoctorsAppointmentId {
                return DoctorsAppointmentId(id)
            }
        }
    }

    @JvmInline
    value class Place(
        val value: String
    ) {
        companion object {
            fun from(value: String): Place {
                return Place(value)
            }
        }
    }
}
