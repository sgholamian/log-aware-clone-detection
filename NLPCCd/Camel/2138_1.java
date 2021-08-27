//,temp,sample_4132.java,2,15,temp,sample_4135.java,2,15
//,2
public class xxx {
private CachingInjector<?> handleHasConverterAnnotation(TypeConverterRegistry registry, Class<?> type, CachingInjector<?> injector, Method method, boolean allowNull) {
if (isValidConverterMethod(method)) {
int modifiers = method.getModifiers();
if (isAbstract(modifiers) || !isPublic(modifiers)) {
} else {
Class<?> toType = method.getReturnType();
if (toType.equals(Void.class)) {


log.info("ignoring bad converter on type method as a converter method returns a void method");
}
}
}
}

};