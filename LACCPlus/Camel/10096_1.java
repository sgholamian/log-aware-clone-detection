//,temp,ApiMethodHelper.java,469,532,temp,FacebookMethodsTypeHelper.java,309,370
//,3
public class xxx {
    public static Object invokeMethod(Object proxy, ApiMethod method, Map<String, Object> properties)
            throws RuntimeCamelException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Invoking {} with arguments {}", method.getName(), properties);
        }

        final List<String> argNames = method.getArgNames();
        final Object[] values = new Object[argNames.size()];
        final List<Class<?>> argTypes = method.getArgTypes();
        final Class<?>[] types = argTypes.toArray(new Class[argTypes.size()]);
        int index = 0;
        for (String name : argNames) {
            Object value = properties.get(name);

            // is the parameter an array type?
            if (value != null && types[index].isArray()) {
                Class<?> type = types[index];

                if (value instanceof Collection) {
                    // convert collection to array
                    Collection<?> collection = (Collection<?>) value;
                    Object array = Array.newInstance(type.getComponentType(), collection.size());
                    if (array instanceof Object[]) {
                        collection.toArray((Object[]) array);
                    } else {
                        int i = 0;
                        for (Object el : collection) {
                            Array.set(array, i++, el);
                        }
                    }
                    value = array;
                } else if (value.getClass().isArray()
                        && type.getComponentType().isAssignableFrom(value.getClass().getComponentType())) {
                    // convert derived array to super array if needed
                    if (type.getComponentType() != value.getClass().getComponentType()) {
                        final int size = Array.getLength(value);
                        Object array = Array.newInstance(type.getComponentType(), size);
                        for (int i = 0; i < size; i++) {
                            Array.set(array, i, Array.get(value, i));
                        }
                        value = array;
                    }
                } else {
                    throw new IllegalArgumentException(
                            String.format("Cannot convert %s to %s", value.getClass(), type));
                }
            }

            values[index++] = value;
        }

        try {
            return method.getMethod().invoke(proxy, values);
        } catch (Throwable e) {
            if (e instanceof InvocationTargetException) {
                // get API exception
                final Throwable cause = e.getCause();
                e = (cause != null) ? cause : e;
            }
            throw new RuntimeCamelException(
                    String.format("Error invoking %s with %s: %s", method.getName(), properties, e.getMessage()), e);
        }
    }

};