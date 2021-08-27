//,temp,AnnotationTypeConverterLoader.java,350,384,temp,AnnotationTypeConverterLoader.java,315,348
//,3
public class xxx {
    private CachingInjector<?> handleHasConverterAnnotation(
            TypeConverterRegistry registry, Class<?> type,
            CachingInjector<?> injector, Method method, boolean allowNull) {
        if (isValidConverterMethod(method)) {
            int modifiers = method.getModifiers();
            if (isAbstract(modifiers) || !isPublic(modifiers)) {
                LOG.warn(
                        "Ignoring bad converter on type: {} method: {} as a converter method is not a public and concrete method",
                        type.getCanonicalName(), method);
            } else {
                Class<?> toType = method.getReturnType();
                if (toType.equals(Void.class)) {
                    LOG.warn("Ignoring bad converter on type: {} method: {} as a converter method returns a void method",
                            type.getCanonicalName(), method);
                } else {
                    Class<?> fromType = method.getParameterTypes()[0];
                    if (isStatic(modifiers)) {
                        registerTypeConverter(registry, method, toType, fromType,
                                new StaticMethodTypeConverter(method, allowNull));
                    } else {
                        if (injector == null) {
                            injector = new CachingInjector<>(registry, CastUtils.cast(type, Object.class));
                        }
                        registerTypeConverter(registry, method, toType, fromType,
                                new InstanceMethodTypeConverter(injector, method, registry, allowNull));
                    }
                }
            }
        } else {
            LOG.warn("Ignoring bad converter on type: {} method: {} as a converter method should have one parameter",
                    type.getCanonicalName(), method);
        }
        return injector;
    }

};