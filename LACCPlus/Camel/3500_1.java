//,temp,DefaultPackageScanClassResolver.java,96,115,temp,DefaultPackageScanClassResolver.java,75,94
//,3
public class xxx {
    @Override
    public Set<Class<?>> findAnnotated(Set<Class<? extends Annotation>> annotations, String... packageNames) {
        if (packageNames == null) {
            return Collections.emptySet();
        }

        if (log.isDebugEnabled()) {
            log.debug("Searching for annotations of {} in packages: {}", annotations, Arrays.asList(packageNames));
        }

        PackageScanFilter test = getCompositeFilter(new AnnotatedWithAnyPackageScanFilter(annotations, true));
        Set<Class<?>> classes = new LinkedHashSet<>();
        for (String pkg : packageNames) {
            find(test, pkg, classes);
        }

        log.debug("Found: {}", classes);

        return classes;
    }

};