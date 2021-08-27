//,temp,sample_4131.java,2,11,temp,sample_4134.java,2,11
//,2
public class xxx {
private CachingInjector<?> handleHasFallbackConverterAnnotation(TypeConverterRegistry registry, Class<?> type, CachingInjector<?> injector, Method method, boolean allowNull) {
if (isValidFallbackConverterMethod(method)) {
int modifiers = method.getModifiers();
if (isAbstract(modifiers) || !isPublic(modifiers)) {


log.info("ignoring bad fallback converter on type method as a fallback converter method is not a public and concrete method");
}
}
}

};