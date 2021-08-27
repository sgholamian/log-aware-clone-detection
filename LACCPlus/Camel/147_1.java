//,temp,DefaultCamelBeanPostProcessor.java,419,431,temp,DefaultCamelBeanPostProcessor.java,407,417
//,2
public class xxx {
    public void setterPropertyInjection(
            Method method, String propertyValue, String propertyDefaultValue,
            Object bean, String beanName) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            LOG.warn("Ignoring badly annotated method for injection due to incorrect number of parameters: {}", method);
        } else {
            String propertyName = org.apache.camel.util.ObjectHelper.getPropertyName(method);
            Object value = getPostProcessorHelper().getInjectionPropertyValue(parameterTypes[0], propertyValue,
                    propertyDefaultValue, propertyName, bean, beanName);
            invokeMethod(method, bean, value);
        }
    }

};