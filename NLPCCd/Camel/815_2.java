//,temp,sample_4252.java,2,14,temp,sample_4251.java,2,11
//,3
public class xxx {
public void appendBuilders(List<RoutesBuilder> list) throws IllegalAccessException, InstantiationException {
Set<Class<?>> classes = resolver.findImplementations(RoutesBuilder.class, packages);
for (Class<?> aClass : classes) {
if (shouldIgnoreBean(aClass)) {


log.info("ignoring routebuilder class");
}
}
}

};