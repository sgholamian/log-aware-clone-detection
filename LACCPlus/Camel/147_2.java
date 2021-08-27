//,temp,DefaultCamelBeanPostProcessor.java,419,431,temp,DefaultCamelBeanPostProcessor.java,407,417
//,2
public class xxx {
    public void setterInjection(Method method, Object bean, String beanName, String endpointUri, String endpointProperty) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            LOG.warn("Ignoring badly annotated method for injection due to incorrect number of parameters: {}", method);
        } else {
            String propertyName = org.apache.camel.util.ObjectHelper.getPropertyName(method);
            Object value = getPostProcessorHelper().getInjectionValue(parameterTypes[0], endpointUri, endpointProperty,
                    propertyName, bean, beanName);
            invokeMethod(method, bean, value);
        }
    }

};