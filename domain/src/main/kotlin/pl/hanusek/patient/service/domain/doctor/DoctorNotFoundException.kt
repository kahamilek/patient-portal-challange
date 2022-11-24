package pl.hanusek.patient.service.domain.doctor

class DoctorNotFoundException(doctorId: Doctor.DoctorId) : Exception("Patient with id $doctorId not exists")
