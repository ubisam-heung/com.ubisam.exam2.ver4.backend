package backend.domain.properties;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.hateoas.Link;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LinkConverter implements AttributeConverter<Link, String> {

    protected Log logger = LogFactory.getLog(getClass());

    // private final Link convertToEntityAttribute = Link.of(".").withSelfRel();
    // private final String convertToDatabaseColumn = ".";//Link.of(".").withSelfRel();

    // private static ObjectMapper objectMapper;

    // @Autowired
    // public void setObjectMapper(ObjectMapper objectMapper) {
    //     LinkConverter.objectMapper = objectMapper;
    // }


	@Override
	public String convertToDatabaseColumn(Link attribute) {
		logger.info("convertToDatabaseColumn: "+attribute);
        // return null;
        return attribute != null ? attribute.getHref() : null;
	}

	@Override
	public Link convertToEntityAttribute(String dbData) {
		logger.info("convertToEntityAttribute: "+dbData);
        return dbData != null ? Link.of(dbData) : null;// convertToEntityAttribute;//link;//Link.of(".").withSelfRel();
		// return Link.of(".").withSelfRel();
        // return Link.of(".");
		// return null;
	}


    // @Override
    // public String convertToDatabaseColumn(Link attribute) {
    //    logger.info("convertToDatabaseColumn: "+attribute);
    //     return attribute != null ? attribute.getHref() : null;
    // }

    // @Override
    // public Link convertToEntityAttribute(String dbData) {
    //    logger.info("convertToEntityAttribute: "+dbData);
    //    Link.of(".").withSelfRel();
    //     return dbData != null ? Link.of(dbData) : null;// convertToEntityAttribute;//link;//Link.of(".").withSelfRel();
    // }

//    public static <T> T convert(){
//        try {
//            String last = UriComponentsBuilder.fromUriString(uri).build().getPathSegments().stream().reduce((first, second) -> second).orElse(null);
//            this.seq = Long.parseLong(last);
//        }catch(Exception e) {
//        }
//
//    }
}
