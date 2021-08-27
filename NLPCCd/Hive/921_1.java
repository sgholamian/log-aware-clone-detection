//,temp,sample_4661.java,2,15,temp,sample_4663.java,2,15
//,3
public class xxx {
private static Class<?>[] getMapArgs(Field field) {
Type genericType = field.getGenericType();
if (genericType instanceof ParameterizedType) {
Type[] types = ((ParameterizedType)genericType).getActualTypeArguments();
if (types.length == 2 && types[0] instanceof Class<?> && types[1] instanceof Class<?>) {
return new Class<?>[] { (Class<?>)types[0], (Class<?>)types[1] };
} else {
}
} else {


log.info("non parametrized map type");
}
}

};