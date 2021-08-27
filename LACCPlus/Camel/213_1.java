//,temp,ResolverHelper.java,122,133,temp,ResolverHelper.java,104,116
//,2
public class xxx {
    public static Language lookupLanguageInRegistryWithFallback(
            CamelContext context, String name, LookupExceptionHandler exceptionHandler) {
        Object bean = lookupInRegistry(context, Language.class, false, exceptionHandler, name, name + LANGUAGE_FALLBACK_SUFFIX);
        if (bean instanceof Language) {
            return (Language) bean;
        }

        if (bean != null) {
            LOG.debug("Found Language with incompatible class: {}", bean.getClass().getName());
        }
        return null;
    }

};