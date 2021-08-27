//,temp,sample_3093.java,2,11,temp,sample_3924.java,2,16
//,3
public class xxx {
public void appendBuilders(List<RoutesBuilder> list) {
Map<String, RoutesBuilder> beans = BlueprintContainerRegistry.lookupByType(blueprintContainer, RoutesBuilder.class, includeNonSingletons);
for (Entry<String, RoutesBuilder> entry : beans.entrySet()) {
String key = entry.getKey();
Object bean = entry.getValue();


log.info("found routebuilder with id");
}
}

};