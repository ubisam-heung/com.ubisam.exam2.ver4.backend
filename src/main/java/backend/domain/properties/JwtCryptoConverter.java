package backend.domain.properties;

import jakarta.persistence.AttributeConverter;

// @Converter(autoApply = true)
public class JwtCryptoConverter implements AttributeConverter<JwtCrypto, String> {

    @Override
    public String convertToDatabaseColumn(JwtCrypto attribute) {

        return null;
    }

    @Override
    public JwtCrypto convertToEntityAttribute(String dbData) {

        return null;
    }
}
