//,temp,sample_2065.java,2,11,temp,sample_2066.java,2,12
//,3
public class xxx {
private MethodInfo introspect(Class<?> clazz, Method method) {
String opName = method.getName();
MethodInfo methodInfo = createMethodInfo(clazz, method);
MethodInfo existingMethodInfo = findMostSpecificOverride(methodInfo);
if (existingMethodInfo != null) {
return existingMethodInfo;
}


log.info("adding operation for method");
}

};