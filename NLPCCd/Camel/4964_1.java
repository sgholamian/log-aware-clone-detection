//,temp,sample_8129.java,2,14,temp,sample_8130.java,2,16
//,3
public class xxx {
public void appendBuilders(List<RoutesBuilder> list) throws IllegalAccessException, InstantiationException {
Set<Class<?>> classes = resolver.findImplementations(RoutesBuilder.class, packages);
for (Class<?> aClass : classes) {
if (shouldIgnoreBean(aClass)) {
continue;
}
if (!isValidClass(aClass)) {


log.info("ignoring invalid routebuilder class");
}
}
}

};