//,temp,sample_2723.java,2,15,temp,sample_5628.java,2,15
//,2
public class xxx {
private static Method getGetMethod(Object o, String propName) {
Method method = null;
String methodName = getGetMethodName("get", propName);
try {
method = o.getClass().getMethod(methodName);
} catch (SecurityException e1) {
} catch (NoSuchMethodException e1) {
if (s_logger.isTraceEnabled()) {


log.info("pojo does not have method for property will check is prefixed method to see if it is boolean property");
}
}
}

};