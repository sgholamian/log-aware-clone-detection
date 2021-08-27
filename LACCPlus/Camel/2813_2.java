//,temp,DefaultCamelBeanPostProcessor.java,443,451,temp,DefaultCamelBeanPostProcessor.java,433,441
//,2
public class xxx {
    public void setterBeanInjection(Method method, String name, Object bean, String beanName) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            LOG.warn("Ignoring badly annotated method for injection due to incorrect number of parameters: {}", method);
        } else {
            Object value = getPostProcessorHelper().getInjectionBeanValue(parameterTypes[0], name);
            invokeMethod(method, bean, value);
        }
    }

};