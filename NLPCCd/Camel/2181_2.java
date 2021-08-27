//,temp,sample_3465.java,2,12,temp,sample_3463.java,2,12
//,2
public class xxx {
public static DataFormat lookupDataFormatInRegistryWithFallback(CamelContext context, String name, LookupExceptionHandler exceptionHandler) {
Object bean = lookupInRegistry(context, DataFormat.class, false, exceptionHandler, name, name + DATA_FORMAT_FALLBACK_SUFFIX);
if (bean instanceof DataFormat) {
return (DataFormat) bean;
}
if (bean != null) {


log.info("found dataformat with incompatible class");
}
}

};