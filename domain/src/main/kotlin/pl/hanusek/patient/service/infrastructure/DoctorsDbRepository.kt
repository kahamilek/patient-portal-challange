package pl.hanusek.patient.service.infrastructure

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import pl.hanusek.patient.service.domain.OrderType
import pl.hanusek.patient.service.domain.doctor.Doctor
import pl.hanusek.patient.service.domain.doctor.DoctorsRepository

@Component
internal class DoctorsDbRepository(
    private val doctorsJpaRepository: DoctorsJpaRepository
) : DoctorsRepository {
    override fun save(patient: Doctor): Doctor {
        return doctorsJpaRepository.save(patient)
    }

    override fun findById(patientId: Doctor.DoctorId): Doctor? {
        return doctorsJpaRepository.findById(patientId.value)
            .orElse(null)
    }

    override fun updateDoctor(patient: Doctor): Doctor {
        return save(patient)
    }

    override fun getDoctorsOnPage(pageNumber: Int, pageSize: Int, orderType: OrderType): Page<Doctor> {
        return doctorsJpaRepository.findAll(
            PageRequest.of(
                pageNumber,
                pageSize,
                sortByProperty("creationTimestamp", orderType)
            )
        )
    }

    override fun delete(patientToRemove: Doctor) {
        doctorsJpaRepository.delete(patientToRemove)
    }

}


internal interface DoctorsJpaRepository : JpaRepository<Doctor, String>


private fun sortByProperty(property: String, orderType: OrderType): Sort {
    val sort = Sort.by(property)

    return when (orderType) {
        OrderType.ASC -> sort.ascending()
        OrderType.DESC -> sort.descending()
    }
}

