package pl.hanusek.patient.service.domain.patient

import org.springframework.data.domain.Page

interface PatientsRepository {

    fun save(patient: Patient): Patient

    fun findById(patientId: Patient.PatientId): Patient?

    fun updatePatient(patient: Patient): Patient

    fun getPatientsOnPage(pageNumber: Int): Page<Patient>
}
