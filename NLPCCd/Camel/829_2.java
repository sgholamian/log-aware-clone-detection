//,temp,sample_3093.java,2,11,temp,sample_3924.java,2,16
//,3
public class xxx {
public void appendBuilders(List<RoutesBuilder> list) {
Map<String, RoutesBuilder> beans = applicationContext.getBeansOfType(RoutesBuilder.class, includeNonSingletons, true);
for (Entry<String, RoutesBuilder> entry : beans.entrySet()) {
Object bean = entry.getValue();
Object key = entry.getKey();
if (shouldIgnoreBean(bean)) {
continue;
}
if (!isFilteredClass(bean)) {


log.info("ignoring filtered routebuilder id as class");
}
}
}

};