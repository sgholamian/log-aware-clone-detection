//,temp,sample_5627.java,2,12,temp,sample_2722.java,2,12
//,2
public class xxx {
private static Method getGetMethod(Object o, String propName) {
Method method = null;
String methodName = getGetMethodName("get", propName);
try {
method = o.getClass().getMethod(methodName);
} catch (SecurityException e1) {


log.info("security exception in getting pojo get method for property");
}
}

};