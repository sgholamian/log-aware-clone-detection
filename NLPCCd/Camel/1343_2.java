//,temp,sample_3923.java,2,13,temp,sample_3094.java,2,13
//,3
public class xxx {
public void appendBuilders(List<RoutesBuilder> list) {
Map<String, RoutesBuilder> beans = BlueprintContainerRegistry.lookupByType(blueprintContainer, RoutesBuilder.class, includeNonSingletons);
for (Entry<String, RoutesBuilder> entry : beans.entrySet()) {
String key = entry.getKey();
Object bean = entry.getValue();
if (shouldIgnoreBean(bean)) {


log.info("ignoring routebuilder id");
}
}
}

};