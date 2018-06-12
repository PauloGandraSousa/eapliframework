/**
 *
 */
package eapli.framework.dto;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.util.Preconditions;
import eapli.framework.util.predicates.StringPredicates;

/**
 * A generic DTO that implements the Map interface. This class can be used when
 * you don't want to build your own custom DTO classes.
 *
 * @author Paulo Gandra Sousa
 *
 */
public class GenericDTO implements DTO, Map<String, Object> {

    private static final Logger LOGGER = LogManager.getLogger(GenericDTO.class);

    private final Map<String, Object> data = new HashMap<>();
    private final String type;

    public GenericDTO(final String type) {
        Preconditions.ensure(() -> !StringPredicates.isNullOrWhiteSpace(type));

        this.type = type;
    }

    /**
     * Builds a DTO from an object using reflection.
     *
     * @param o
     * @return
     */
    public static GenericDTO of(final Object o) {
        final GenericDTO out = new GenericDTO(o.getClass().getName());
        final List<Field> fields = getInheritedFields(o.getClass());
        for (final Field aField : fields) {
            ofField(o, out, aField);
        }
        return out;
    }

    @SuppressWarnings("unchecked")
    private static void ofField(final Object o, final GenericDTO out, final Field aField) {
        try {
            aField.setAccessible(true);
            if (aField.getType().isPrimitive() || aField.getType() == String.class) {
                out.put(aField.getName(), aField.get(o));
            } else if (aField.getType().isArray()) {
                if (aField.getType().getComponentType().isPrimitive()
                        || aField.getType().getComponentType() == String.class) {
                    buildDtoForArray(aField.getType().getComponentType(), aField.getName(), aField.get(o), out);
                } else {
                    buildDtoForIterable(aField.getName(), (Iterable<Object>) (aField.get(o)), out);
                }
            } else if (Collection.class.isAssignableFrom(aField.getType())) {
                buildDtoForIterable(aField.getName(), (Iterable<Object>) (aField.get(o)), out);
            } else {
                out.put(aField.getName(), of(aField.get(o)));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOGGER.error(e);
        }
    }

    public static Iterable<GenericDTO> ofMany(final Iterable<?> col) {
        final List<GenericDTO> data = new ArrayList<>();
        for (final Object member : col) {
            data.add(of(member));
        }
        return data;
    }

    /**
     */
    private static void buildDtoForIterable(final String name, final Iterable<Object> col, final GenericDTO out) {

        final Iterable<GenericDTO> data = ofMany(col);
        out.put(name, data);
    }

    /**
     */
    private static void buildDtoForArray(final Class<?> type, final String name, final Object array, final GenericDTO out) {
        final int length = Array.getLength(array);
        Object data = null;
        if (type == int.class) {
            data = Arrays.copyOf((int[]) array, length);
        } else if (type == long.class) {
            data = Arrays.copyOf((long[]) array, length);
        } else if (type == boolean.class) {
            data = Arrays.copyOf((boolean[]) array, length);
        } else if (type == double.class) {
            data = Arrays.copyOf((double[]) array, length);
        } else if (type == float.class) {
            data = Arrays.copyOf((float[]) array, length);
        } else if (type == char.class) {
            data = Arrays.copyOf((char[]) array, length);
        } else if (type == String.class) {
            data = Arrays.copyOf((String[]) array, length);
        }

        out.put(name, data);
    }

    public static Object valueOf(final GenericDTO dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private static List<Field> getInheritedFields(final Class<?> type) {
        final List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * Returns the name of the type contained in this DTO. Might be helpful for
     * client code to parse the DTO.
     *
     * @return
     */
    public String type() {
        return type;
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean containsKey(final Object arg0) {
        return data.containsKey(arg0);
    }

    @Override
    public boolean containsValue(final Object arg0) {
        return data.containsValue(arg0);
    }

    @Override
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return data.entrySet();
    }

    @Override
    public Object get(final Object arg0) {
        return data.get(arg0);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @Override
    public Object put(final String arg0, final Object arg1) {
        return data.put(arg0, arg1);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends Object> arg0) {
        data.putAll(arg0);
    }

    @Override
    public Object remove(final Object arg0) {
        return data.remove(arg0);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Collection<Object> values() {
        return data.values();
    }
}
