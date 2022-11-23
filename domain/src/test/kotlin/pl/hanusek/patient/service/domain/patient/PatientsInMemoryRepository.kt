package pl.hanusek.patient.service.domain.patient

import org.springframework.data.domain.Page
import java.util.concurrent.ConcurrentHashMap

class PatientsInMemoryRepository : PatientsRepository {
    private val patientByPatientId = ConcurrentHashMap<Patient.PatientId, Patient>()

    override fun save(patient: Patient): Patient {
        return patientByPatientId.putIfAbsent(patient.id, patient)
            ?: patientByPatientId.getValue(patient.id)
    }

    override fun findById(patientId: Patient.PatientId): Patient? {
        return patientByPatientId[patientId]
    }

    override fun updatePatient(patient: Patient): Patient {
        patientByPatientId.put(patient.id, patient)
        return patient
    }

    override fun getPatientsOnPage(pageNumber: Int): Page<Patient> {
        TODO("Not yet implemented")
    }

}
