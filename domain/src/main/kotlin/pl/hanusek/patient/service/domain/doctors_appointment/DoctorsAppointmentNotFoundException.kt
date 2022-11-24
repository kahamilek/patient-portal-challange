package pl.hanusek.patient.service.domain.doctors_appointment

class DoctorsAppointmentNotFoundException(doctorsAppointmentId: DoctorsAppointment.DoctorsAppointmentId) : Exception("Doctors appointment with id $doctorsAppointmentId not exists")
