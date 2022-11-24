package pl.hanusek.patient.service.domain

data class Pageable(
    val pageSize: Int,
    val pageNumber: Int,
    val orderType: OrderType
)
