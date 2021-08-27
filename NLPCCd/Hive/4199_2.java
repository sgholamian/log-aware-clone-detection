//,temp,sample_4660.java,2,14,temp,sample_4662.java,2,14
//,3
public class xxx {
private static Class<?> getCollectionArg(Field field) {
Type genericType = field.getGenericType();
if (genericType instanceof ParameterizedType) {
Type type = ((ParameterizedType)genericType).getActualTypeArguments()[0];
if (type instanceof Class<?>) {
return (Class<?>)type;
} else {


log.info("cannot determine collection type");
}
}
}

};