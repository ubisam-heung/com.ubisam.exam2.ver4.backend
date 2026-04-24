package backend.domain.properties;

import org.springframework.data.repository.CrudRepository;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 엔티티의 특정 필드값으로 객체를 찾는 유틸리티
 */
public class EntityFieldValueFinder<T, ID> {
    private final CrudRepository<T, ID> repository;
    private final Set<String> fieldKeys;

    public EntityFieldValueFinder(CrudRepository<T, ID> repository, String[] fieldKeys) {
        this.repository = repository;
        this.fieldKeys = new LinkedHashSet<>();
        if (fieldKeys != null) {
            Arrays.stream(fieldKeys)
                .filter(fieldKey -> fieldKey != null && !fieldKey.trim().isEmpty())
                .map(String::trim)
                .forEach(this.fieldKeys::add);
        }
    }

    public List<T> find(Map<String, Object> criteria) {
        Map<String, Object> normalizedCriteria = normalizeCriteria(criteria);
        if (normalizedCriteria.isEmpty()) {
            return List.of();
        }
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .filter(entity -> matchesAllCriteria(entity, normalizedCriteria))
            .collect(Collectors.toList());
    }

    private Map<String, Object> normalizeCriteria(Map<String, Object> criteria) {
        Map<String, Object> normalized = new LinkedHashMap<>();
        if (criteria == null) {
            return normalized;
        }
        criteria.forEach((fieldKey, value) -> {
            if (value == null || !fieldKeys.contains(fieldKey)) {
                return;
            }
            if (value instanceof String stringValue && stringValue.trim().isEmpty()) {
                return;
            }
            normalized.put(fieldKey, value);
        });
        return normalized;
    }

    private boolean matchesAllCriteria(T entity, Map<String, Object> criteria) {
        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            Object entityValue = readEntityValue(entity, entry.getKey());
            if (!matches(entityValue, entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    private Object readEntityValue(T entity, String entityPath) {
        if (entity == null || entityPath == null || entityPath.isBlank()) {
            return null;
        }
        try {
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);
            return beanWrapper.getPropertyValue(entityPath);
        } catch (BeansException exception) {
            return null;
        }
    }

    private boolean matches(Object entityValue, Object criterionValue) {
        if (entityValue == null || criterionValue == null) {
            return false;
        }
        if (criterionValue instanceof Collection<?> criterionValues) {
            return criterionValues.stream().anyMatch(value -> matchesSingle(entityValue, value));
        }
        return matchesSingle(entityValue, criterionValue);
    }

    private boolean matchesSingle(Object entityValue, Object criterionValue) {
        if (entityValue == null || criterionValue == null) {
            return false;
        }
        if (entityValue instanceof Number entityNumber && criterionValue instanceof Number criterionNumber) {
            return Double.compare(entityNumber.doubleValue(), criterionNumber.doubleValue()) == 0;
        }
        if (entityValue instanceof Boolean entityBoolean && criterionValue instanceof Boolean criterionBoolean) {
            return entityBoolean.equals(criterionBoolean);
        }
        String entityText = entityValue.toString();
        String criterionText = criterionValue.toString().trim();
        if (criterionText.isEmpty()) {
            return false;
        }
        if (entityValue instanceof Number) {
            return entityText.equals(criterionText);
        }
        return entityText.contains(criterionText);
    }
}
