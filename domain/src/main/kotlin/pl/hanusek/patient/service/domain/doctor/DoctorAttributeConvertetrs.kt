package pl.hanusek.patient.service.domain.doctor

import pl.hanusek.patient.service.domain.FullName
import pl.hanusek.patient.service.shared.Jackson
import javax.persistence.AttributeConverter

internal object FullNameAttributeConverter : AttributeConverter<FullName, String> {
    override fun convertToDatabaseColumn(attribute: FullName): String {
        return mapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): FullName {
        return mapper.readValue(dbData, FullName::class.java)
    }
}

private val mapper = Jackson.mapper
