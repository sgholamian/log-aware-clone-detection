//,temp,ResolverHelper.java,86,98,temp,ResolverHelper.java,59,80
//,3
public class xxx {
    public static Component lookupComponentInRegistryWithFallback(
            CamelContext context, String name, LookupExceptionHandler exceptionHandler) {
        Object bean
                = lookupInRegistry(context, Component.class, false, exceptionHandler, name, name + COMPONENT_FALLBACK_SUFFIX);
        if (bean != null) {
            if (bean instanceof Component) {
                return (Component) bean;
            } else {
                // let's use Camel's type conversion mechanism to convert things like CamelContext
                // and other types into a valid Component
                Component component = CamelContextHelper.convertTo(context, Component.class, bean);
                if (component != null) {
                    return component;
                }
            }
        }

        if (bean != null) {
            LOG.debug("Found Component with incompatible class: {}", bean.getClass().getName());
        }
        return null;
    }

};