package backend.domain.properties;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;

public final class JsonNodeValueExtractor {

    private JsonNodeValueExtractor() {
    }

    public static Map<String, Object> extractConfiguredValues(JsonNode payloadNode, Collection<String> fieldKeys) {
        Map<String, Object> values = new LinkedHashMap<>();
        if (payloadNode == null || fieldKeys == null) {
            return values;
        }
        for (String fieldKey : fieldKeys) {
            if (fieldKey == null) {
                continue;
            }
            String trimmedKey = fieldKey.trim();
            if (trimmedKey.isEmpty() || !payloadNode.hasNonNull(trimmedKey)) {
                continue;
            }
            Object value = extractValue(payloadNode.get(trimmedKey));
            if (value != null) {
                values.put(trimmedKey, value);
            }
        }
        return values;
    }

    public static Object extractValue(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) {
            return null;
        }
        if (node.isInt() || node.isLong()) {
            return node.asLong();
        }
        if (node.isFloatingPointNumber()) {
            return node.asDouble();
        }
        if (node.isBoolean()) {
            return node.asBoolean();
        }
        String value = node.asText("").trim();
        if (value.contains(",")) {
            List<String> values = Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
            return values.isEmpty() ? null : values;
        }
        return value.isEmpty() ? null : value;
    }

    public static String extractTextValue(JsonNode node) {
        Object value = extractValue(node);
        return value == null ? null : value.toString();
    }

    public static Integer extractIntegerValue(JsonNode node) {
        Object value = extractValue(node);
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        try {
            return Integer.valueOf(value.toString().trim());
        } catch (NumberFormatException ignore) {
            return null;
        }
    }
}