//,temp,ResolverHelper.java,122,133,temp,ResolverHelper.java,104,116
//,2
public class xxx {
    public static DataFormatFactory lookupDataFormatFactoryInRegistryWithFallback(
            CamelContext context, String name, LookupExceptionHandler exceptionHandler) {
        Object bean = lookupInRegistry(context, DataFormatFactory.class, false, exceptionHandler, name,
                name + DATA_FORMAT_FACTORY_FALLBACK_SUFFIX);
        if (bean instanceof DataFormatFactory) {
            return (DataFormatFactory) bean;
        }

        if (bean != null) {
            LOG.debug("Found DataFormatFactory with incompatible class: {}", bean.getClass().getName());
        }
        return null;
    }

};