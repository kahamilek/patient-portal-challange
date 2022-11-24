package pl.hanusek.patient.service.domain.doctor

class DoctorNotFoundException(doctorId: Doctor.DoctorId) : Exception("Doctor with id $doctorId not exists")
