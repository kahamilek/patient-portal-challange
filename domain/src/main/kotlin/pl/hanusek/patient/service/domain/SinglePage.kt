package pl.hanusek.patient.service.domain

data class SinglePage<T>(
    val pageNumber: Int,
    val pageSize: Int,
    val totalNumberOfPages: Int,
    val elementsOnCurrentPage: List<T>
)
