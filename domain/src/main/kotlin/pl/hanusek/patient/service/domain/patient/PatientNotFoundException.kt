package pl.hanusek.patient.service.domain.patient

class PatientNotFoundException(patientId: Patient.PatientId) : Exception("Patient with id $patientId not exists")
