package pl.hanusek.patient.service.domain

import org.springframework.data.domain.Page

data class SinglePage<T>(
    val pageNumber: Int,
    val pageSize: Int,
    val totalNumberOfPages: Int,
    val elementsOnCurrentPage: List<T>
)

internal fun <T> Page<T>.toDomainModel(): SinglePage<T> {
    return SinglePage(
        pageNumber = this.pageable.pageNumber,
        pageSize = pageable.pageSize,
        totalNumberOfPages = totalPages,
        elementsOnCurrentPage = this.content,
    )
}
