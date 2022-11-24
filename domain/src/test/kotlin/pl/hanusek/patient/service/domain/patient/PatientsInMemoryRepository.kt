package pl.hanusek.patient.service.domain.patient

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
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

    override fun getPatientsOnPage(
        pageNumber: Int,
        pageSize: Int,
        orderType: PatientsFacade.OrderType
    ): Page<Patient> {
        val allElementsSorted = patientByPatientId.values.sorted(orderType)

        return pageOf(
            allElementsSorted.drop(pageNumber * pageSize)
                .take(pageSize),
            pageNumber,
            pageSize
        )
    }

    override fun delete(patientToRemove: Patient) {
        patientByPatientId.remove(patientToRemove.id)
    }

}


private fun pageOf(elements: List<Patient>, pageNumber: Int, pageSize: Int): Page<Patient> {
    return PageImpl(elements, PageRequest.of(pageNumber, pageSize), elements.size.toLong())
}

private fun MutableCollection<Patient>.sorted(orderType: PatientsFacade.OrderType): List<Patient> {
    return when (orderType) {
        PatientsFacade.OrderType.ASC -> this.sortedBy { it.creationTimestamp }
        PatientsFacade.OrderType.DESC -> this.sortedByDescending { it.creationTimestamp }
    }
}
