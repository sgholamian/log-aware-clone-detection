//,temp,sample_8591.java,2,10,temp,sample_8590.java,2,10
//,2
public class xxx {
private Boolean equal(Class<?> cls, Object... parameters) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
String equalMethod = equalMethodPrefix + getClassName(cls);
Method method = _methodMap.get(equalMethod);
if (method == null) {


log.info("method not implemented");
}
}

};