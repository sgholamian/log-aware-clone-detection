//,temp,AnnotationTypeConverterLoader.java,350,384,temp,AnnotationTypeConverterLoader.java,315,348
//,3
public class xxx {
    private CachingInjector<?> handleHasFallbackConverterAnnotation(
            TypeConverterRegistry registry, Class<?> type,
            CachingInjector<?> injector, Method method, boolean allowNull) {
        if (isValidFallbackConverterMethod(method)) {
            int modifiers = method.getModifiers();
            if (isAbstract(modifiers) || !isPublic(modifiers)) {
                LOG.warn("Ignoring bad fallback converter on type: {} method: {} as a fallback converter method is not "
                         + "a public and concrete method",
                        type.getCanonicalName(), method);
            } else {
                Class<?> toType = method.getReturnType();
                if (toType.equals(Void.class)) {
                    LOG.warn("Ignoring bad fallback converter on type: {} method: {} as a fallback converter method "
                             + "returns a void method",
                            type.getCanonicalName(), method);
                } else {
                    if (isStatic(modifiers)) {
                        registerFallbackTypeConverter(registry,
                                new StaticMethodFallbackTypeConverter(method, registry, allowNull), method);
                    } else {
                        if (injector == null) {
                            injector = new CachingInjector<>(registry, CastUtils.cast(type, Object.class));
                        }
                        registerFallbackTypeConverter(registry,
                                new InstanceMethodFallbackTypeConverter(injector, method, registry, allowNull), method);
                    }
                }
            }
        } else {
            LOG.warn("Ignoring bad fallback converter on type: {} method: {} as a fallback converter method should have "
                     + "one parameter",
                    type.getCanonicalName(), method);
        }
        return injector;
    }

};