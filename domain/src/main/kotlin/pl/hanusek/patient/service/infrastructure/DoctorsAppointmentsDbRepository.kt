package pl.hanusek.patient.service.infrastructure

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pl.hanusek.patient.service.domain.OrderType
import pl.hanusek.patient.service.domain.Pageable
import pl.hanusek.patient.service.domain.doctors_appointment.DoctorsAppointment
import pl.hanusek.patient.service.domain.doctors_appointment.DoctorsAppointmentsRepository
import pl.hanusek.patient.service.domain.patient.Patient

@Repository
internal class DoctorsAppointmentsDbRepository(
    private val jpaRepository: DoctorsAppointmentJpaRepository
) : DoctorsAppointmentsRepository {

    override fun addNewDoctorsAppointment(doctorsAppointment: DoctorsAppointment): DoctorsAppointment {
        return jpaRepository.save(doctorsAppointment)
    }

    override fun updateDoctorsAppointment(doctorsAppointment: DoctorsAppointment): DoctorsAppointment {
        return jpaRepository.save(doctorsAppointment)
    }

    override fun removeDoctorAppointment(doctorsAppointment: DoctorsAppointment) {
        jpaRepository.delete(doctorsAppointment)
    }

    override fun findDoctorAppointmentById(doctorsAppointmentId: DoctorsAppointment.DoctorsAppointmentId): DoctorsAppointment? {
        return jpaRepository.findById(doctorsAppointmentId.value).orElse(null)
    }

    override fun getAllDoctorsAppointment(patientId: Patient.PatientId?, pageable: Pageable): Page<DoctorsAppointment> {
        val pageRequest = PageRequest.of(
            pageable.pageNumber,
            pageable.pageSize,
            sortByProperty("creationTimestamp", pageable.orderType)
        )
        return if (patientId == null) {
            jpaRepository.findAll(pageRequest)
        } else {
            jpaRepository.findAllByPatientId(patientId.value, pageRequest)
        }
    }
}

internal interface DoctorsAppointmentJpaRepository :
    JpaRepository<DoctorsAppointment, String> {

    fun findAllByPatientId(
        patientId: String,
        pageable: org.springframework.data.domain.Pageable
    ): Page<DoctorsAppointment>
}

private fun sortByProperty(property: String, orderType: OrderType): Sort {
    val sort = Sort.by(property)

    return when (orderType) {
        OrderType.ASC -> sort.ascending()
        OrderType.DESC -> sort.descending()
    }
}
