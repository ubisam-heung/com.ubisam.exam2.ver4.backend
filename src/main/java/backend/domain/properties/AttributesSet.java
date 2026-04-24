package backend.domain.properties;

import java.util.Collection;
import java.util.HashSet;

public class AttributesSet extends HashSet<Object>{
  
    private String text;

    public AttributesSet() {
        super();
    }

    public AttributesSet(String text) {
        super();
        this.text = text;
    }
    public AttributesSet(Collection<? extends Object> collection) {
        super(collection);
    }

    public boolean hasText() {
        return text != null;
    }

    public String getText() {
        return text;
    }

    @SuppressWarnings("unchecked")
    public static <T> AttributesSet of(T... values){
        AttributesSet a = new AttributesSet();
        for(T v : values){
            a.add(v);
        }
        return a;
    }
}
