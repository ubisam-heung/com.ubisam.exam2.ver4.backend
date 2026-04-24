package backend.domain.properties;

import java.util.HashMap;
import java.util.Map;


public class AttributesMap extends HashMap<String, Object> {

    private String text;

    public AttributesMap() {
        super();
    }

    public AttributesMap(String text) {
        super();
        this.text = text;
    }
    public AttributesMap(Map<? extends String, ? extends Object> map) {
        super(map);
    }

    public boolean hasText() {
        return text != null;
    }

    public String getText() {
        return text;
    }

    public static AttributesMap of(String key, Object value){
        AttributesMap a = new AttributesMap();
        a.put(key, value);
        return a;
    }
}