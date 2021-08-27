//,temp,sample_2301.java,2,11,temp,sample_316.java,2,11
//,2
public class xxx {
protected void setterBeanInjection(Method method, String name, Object bean, String beanName) {
Class<?>[] parameterTypes = method.getParameterTypes();
if (parameterTypes != null) {
if (parameterTypes.length != 1) {


log.info("ignoring badly annotated method for injection due to incorrect number of parameters");
}
}
}

};