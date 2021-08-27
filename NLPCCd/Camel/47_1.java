//,temp,sample_4250.java,2,9,temp,sample_8128.java,2,11
//,3
public class xxx {
public void appendBuilders(List<RoutesBuilder> list) throws IllegalAccessException, InstantiationException {
Set<Class<?>> classes = resolver.findImplementations(RoutesBuilder.class, packages);
for (Class<?> aClass : classes) {


log.info("found routebuilder class");
}
}

};