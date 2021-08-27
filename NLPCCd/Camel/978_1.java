//,temp,sample_3925.java,2,17,temp,sample_8261.java,2,15
//,3
public class xxx {
public void dummy_method(){
Map<String, RoutesBuilder> beans = applicationContext.getBeansOfType(RoutesBuilder.class, includeNonSingletons, true);
for (Entry<String, RoutesBuilder> entry : beans.entrySet()) {
Object bean = entry.getValue();
Object key = entry.getKey();
if (shouldIgnoreBean(bean)) {
continue;
}
if (!isFilteredClass(bean)) {
continue;
}


log.info("adding instantiated routebuilder id as class");
}
}

};