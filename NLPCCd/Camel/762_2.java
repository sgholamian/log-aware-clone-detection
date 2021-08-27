//,temp,sample_315.java,2,11,temp,sample_314.java,2,11
//,3
public class xxx {
public void setterInjection(Method method, Object bean, String beanName, String endpointUri, String endpointRef, String endpointProperty) {
Class<?>[] parameterTypes = method.getParameterTypes();
if (parameterTypes != null) {
if (parameterTypes.length != 1) {


log.info("ignoring badly annotated method for injection due to incorrect number of parameters");
}
}
}

};