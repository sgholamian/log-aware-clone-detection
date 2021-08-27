//,temp,DefaultPackageScanClassResolver.java,96,115,temp,DefaultPackageScanClassResolver.java,75,94
//,3
public class xxx {
    @Override
    public Set<Class<?>> findAnnotated(Class<? extends Annotation> annotation, String... packageNames) {
        if (packageNames == null) {
            return Collections.emptySet();
        }

        if (log.isDebugEnabled()) {
            log.debug("Searching for annotations of {} in packages: {}", annotation.getName(), Arrays.asList(packageNames));
        }

        PackageScanFilter test = getCompositeFilter(new AnnotatedWithPackageScanFilter(annotation, true));
        Set<Class<?>> classes = new LinkedHashSet<>();
        for (String pkg : packageNames) {
            find(test, pkg, classes);
        }

        log.debug("Found: {}", classes);

        return classes;
    }

};