//,temp,ResolverHelper.java,86,98,temp,ResolverHelper.java,59,80
//,3
public class xxx {
    public static DataFormat lookupDataFormatInRegistryWithFallback(
            CamelContext context, String name, LookupExceptionHandler exceptionHandler) {
        Object bean = lookupInRegistry(context, DataFormat.class, false, exceptionHandler, name,
                name + DATA_FORMAT_FALLBACK_SUFFIX);
        if (bean instanceof DataFormat) {
            return (DataFormat) bean;
        }

        if (bean != null) {
            LOG.debug("Found DataFormat with incompatible class: {}", bean.getClass().getName());
        }
        return null;
    }

};