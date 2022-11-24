package pl.hanusek.patient.service.domain

enum class OrderType {
    ASC,
    DESC;

    companion object {
        const val DEFAULT_ORDER_TYPE_TEXT = "ASC"

        fun from(text: String): OrderType {
            return OrderType.values()
                .firstOrNull { it.toString() == text }
                ?: ASC
        }
    }
}
