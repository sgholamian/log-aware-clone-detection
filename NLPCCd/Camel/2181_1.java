//,temp,sample_3465.java,2,12,temp,sample_3463.java,2,12
//,2
public class xxx {
public static Language lookupLanguageInRegistryWithFallback(CamelContext context, String name, LookupExceptionHandler exceptionHandler) {
Object bean = lookupInRegistry(context, Language.class, false, exceptionHandler, name, name + LANGUAGE_FALLBACK_SUFFIX);
if (bean instanceof Language) {
return (Language) bean;
}
if (bean != null) {


log.info("found language with incompatible class");
}
}

};