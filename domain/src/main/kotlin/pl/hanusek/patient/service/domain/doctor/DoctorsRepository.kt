package pl.hanusek.patient.service.domain.doctor

import org.springframework.data.domain.Page
import pl.hanusek.patient.service.domain.OrderType

internal interface DoctorsRepository {

    fun save(patient: Doctor): Doctor

    fun findById(patientId: Doctor.DoctorId): Doctor?

    fun updateDoctor(patient: Doctor): Doctor

    fun getDoctorsOnPage(pageNumber: Int, pageSize: Int, orderType: OrderType): Page<Doctor>
    fun delete(patientToRemove: Doctor)
}
