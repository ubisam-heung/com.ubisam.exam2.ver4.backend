package backend.domain.properties;

import jakarta.persistence.AttributeConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// @Converter(autoApply = true)
public class StringArrayConverter implements AttributeConverter<String[], String> {
    // private static final String SPLIT_CHAR = ", ";

    @Override
    public String convertToDatabaseColumn(String[] value) {
        return StringUtils.arrayToCommaDelimitedString(value);
    }

    @Override
    public String[] convertToEntityAttribute(String value) {
        return StringUtils.commaDelimitedListToStringArray(value);
    }


    public static boolean contains(String[] source, String value){
        List<String> list = ObjectUtils.isEmpty(source)
                ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(source));
        return list.contains(value);
    }

    public static String[] add(String[] source, String value){
        List<String> list = ObjectUtils.isEmpty(source)
                ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(source));
        if(! list.contains(value)) {
            list.add(value);
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }
    public static String[] remove(String[] source, String value){
        List<String> list = ObjectUtils.isEmpty(source)
                ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(source));
        if(list.contains(value)) {
            list.remove(value);
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

    public static Integer indexOf(String[] source, String value){
        return Arrays.asList(source).indexOf(value);
    }

    public static String[] update(String[] source, Integer idx, String newValue){
        String[] result = Arrays.copyOf(source, source.length);
        result[idx] = newValue;
        return result;
    }
}