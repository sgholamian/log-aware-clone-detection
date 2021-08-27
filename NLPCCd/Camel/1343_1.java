//,temp,sample_3923.java,2,13,temp,sample_3094.java,2,13
//,3
public class xxx {
public void appendBuilders(List<RoutesBuilder> list) {
Map<String, RoutesBuilder> beans = applicationContext.getBeansOfType(RoutesBuilder.class, includeNonSingletons, true);
for (Entry<String, RoutesBuilder> entry : beans.entrySet()) {
Object bean = entry.getValue();
Object key = entry.getKey();
if (shouldIgnoreBean(bean)) {


log.info("ignoring routebuilder id");
}
}
}

};