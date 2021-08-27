//,temp,sample_4661.java,2,15,temp,sample_4663.java,2,15
//,3
public class xxx {
private static Class<?> getCollectionArg(Field field) {
Type genericType = field.getGenericType();
if (genericType instanceof ParameterizedType) {
Type type = ((ParameterizedType)genericType).getActualTypeArguments()[0];
if (type instanceof Class<?>) {
return (Class<?>)type;
} else {
}
} else {


log.info("non parametrized collection type");
}
}

};