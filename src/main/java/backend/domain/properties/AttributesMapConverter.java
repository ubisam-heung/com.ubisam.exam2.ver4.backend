package backend.domain.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Converter(autoApply = true)
public class AttributesMapConverter implements AttributeConverter<AttributesMap, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(AttributesMap attribute) {
        try {
            if (attribute == null) return null;

            if (attribute.hasText()) {
                return attribute.getText();
            } else {
                return mapper.writeValueAsString(attribute);
            }

        } catch(Exception e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public AttributesMap convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null) return null;
            return new AttributesMap(mapper.readValue(dbData, Map.class));
        } catch(Exception e) {
            return null;
        }
    }
}