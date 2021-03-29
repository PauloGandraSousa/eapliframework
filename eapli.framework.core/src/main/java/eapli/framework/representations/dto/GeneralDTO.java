/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.representations.dto;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;

/**
 * A general collection of key-value pairs to work as a DTO avoiding to create your own custom DTO
 * classes. As a convenience, it implements the Map interface.
 *
 * @author Paulo Gandra Sousa
 *
 */
@DTO
public class GeneralDTO implements Map<String, Object> {

    private static final Logger LOGGER = LogManager.getLogger(GeneralDTO.class);

    private final Map<String, Object> data = new ConcurrentHashMap<>();
    private final String type;

    /**
     * Constructs a DTO
     *
     * @param type
     */
    public GeneralDTO(final String type) {
        Preconditions.ensure(!StringPredicates.isNullOrWhiteSpace(type));

        this.type = type;
    }

    /**
     * Builds a DTO from an object using reflection.
     *
     * @param subject
     * @return a GeneralDTO that represents the content of {@code subject}
     */
    public static GeneralDTO of(final Object subject) {
        final GeneralDTO dto = new GeneralDTO(subject.getClass().getName());
        final List<Field> fields = getInheritedFields(subject.getClass());
        for (final Field aField : fields) {
            transformFieldToDTO(subject, aField, dto);
        }
        return dto;
    }

    /**
     * We are ignoring the security issue S3011 since control the execution and know the use we are
     * giving to the reflected fields, eventough care must be taken when using reflection
     *
     * @param subject
     * @param aField
     * @param dto
     */
    @SuppressWarnings({ "unchecked", "squid:S3011" })
    private static void transformFieldToDTO(final Object subject, final Field aField, final GeneralDTO dto) {
        final boolean oldAccessibility = aField.canAccess(subject);
        aField.setAccessible(true);
        try {
            if (aField.getType().isPrimitive() || aField.getType() == String.class) {
                dto.put(aField.getName(), aField.get(subject));
            } else if (aField.getType().isArray()) {
                if (aField.getType().getComponentType().isPrimitive()
                        || aField.getType().getComponentType() == String.class) {
                    buildDtoForArray(aField.getType().getComponentType(), aField.getName(),
                            aField.get(subject), dto);
                } else {
                    buildDtoForIterable(aField.getName(), (Iterable<Object>) (aField.get(subject)),
                            dto);
                }
            } else if (Collection.class.isAssignableFrom(aField.getType())) {
                buildDtoForIterable(aField.getName(), (Iterable<Object>) (aField.get(subject)),
                        dto);
            } else {
                dto.put(aField.getName(), of(aField.get(subject)));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOGGER.error(e);
        } finally {
            aField.setAccessible(oldAccessibility);
        }
    }

    /**
     *
     * @param col
     * @return
     */
    public static Iterable<GeneralDTO> ofMany(final Iterable<?> col) {
        final List<GeneralDTO> data = new ArrayList<>();
        for (final Object member : col) {
            data.add(of(member));
        }
        return data;
    }

    private static void buildDtoForIterable(final String name, final Iterable<Object> col, final GeneralDTO out) {

        final Iterable<GeneralDTO> data = ofMany(col);
        out.put(name, data);
    }

    private static void buildDtoForArray(final Class<?> type, final String name, final Object array,
            final GeneralDTO out) {
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

    /**
     *
     * @param dto
     * @return
     */
    public static Object valueOf(final GeneralDTO dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Gets the list of all non-synthetic fields from the inheritance structure. Synthetic fields
     * are
     * ignored mostly due to JacoCo instrumentation which adds a synthetic field
     * {@code $jacocoData}.
     *
     * <p>
     * We are ignoring the security issue S1523 we since control the execution and know the use we
     * are
     * giving to the reflected fields, eventough care must be taken when using reflection
     *
     * @param type
     * @return the list of all non-synthetic fields from the inheritance structure
     */
    @SuppressWarnings("squid:S1523")
    private static List<Field> getInheritedFields(final Class<?> type) {
        final List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            final List<Field> one = Stream.of(c.getDeclaredFields()).filter(f -> !f.isSynthetic())
                    .collect(Collectors.toList());
            fields.addAll(one);
        }
        return fields;
    }

    /**
     * Returns the name of the type contained in this DTO. Might be helpful for client code to parse
     * the DTO.
     *
     * @return the name of the type contained in this DTO
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
    public Set<Map.Entry<String, Object>> entrySet() {
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
