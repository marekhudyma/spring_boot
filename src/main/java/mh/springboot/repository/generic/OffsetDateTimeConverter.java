package mh.springboot.repository.generic;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Converter(autoApply=true)
@SuppressWarnings("unused")
//JPA next should support Java 8 Date and Time types
//https://java.net/jira/browse/JPA_SPEC-63
public class OffsetDateTimeConverter implements AttributeConverter<OffsetDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(OffsetDateTime offsetDateTime) {
        if (offsetDateTime != null) {
            Instant instant = Instant.from(offsetDateTime);
            return Date.from(instant);
        }

        return null;
    }

    @Override
        public OffsetDateTime convertToEntityAttribute(Date date) {
        if (date != null) {
            Instant instant = date.toInstant();
            return instant.atOffset(ZoneOffset.UTC);
        }

        return null;
    }
}
