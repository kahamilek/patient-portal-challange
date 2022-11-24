package pl.hanusek.patient.service.infrastructure

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import pl.hanusek.patient.service.domain.OrderType
import pl.hanusek.patient.service.domain.patient.Patient
import pl.hanusek.patient.service.domain.patient.PatientsFacade
import pl.hanusek.patient.service.domain.patient.PatientsRepository

@Component
internal class PatientsDbRepository(
    private val patientJpaRepository: PatientJpaRepository
) : PatientsRepository {
    override fun save(patient: Patient): Patient {
        return patientJpaRepository.save(patient)
    }
    override fun findById(patientId: Patient.PatientId): Patient? {
        return patientJpaRepository.findById(patientId.value)
            .orElse(null)
    }

    override fun updatePatient(patient: Patient): Patient {
        return save(patient)
    }

    override fun getPatientsOnPage(pageNumber: Int, pageSize: Int, orderType: OrderType): Page<Patient> {
        return patientJpaRepository.findAll(
            PageRequest.of(
                pageNumber,
                pageSize,
                sortByProperty("creationTimestamp",orderType)
            )
        )
    }

    override fun delete(patientToRemove: Patient) {
        patientJpaRepository.delete(patientToRemove)
    }

}


internal interface PatientJpaRepository : JpaRepository<Patient, String>


private fun sortByProperty(property: String, orderType: OrderType): Sort {
    val sort = Sort.by(property)

    return when (orderType) {
        OrderType.ASC -> sort.ascending()
        OrderType.DESC -> sort.descending()
    }
}
