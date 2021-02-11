//,temp,sample_8591.java,2,10,temp,sample_8590.java,2,10
//,2
public class xxx {
private Boolean filter(Class<?> cls, Object... parameters) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
String filterMethod = filterMethodPrefix + getClassName(cls);
Method method = _methodMap.get(filterMethod);
if (method == null) {


log.info("method not implemented");
}
}

};