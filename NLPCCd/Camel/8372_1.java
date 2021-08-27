//,temp,sample_6460.java,2,11,temp,sample_6459.java,2,11
//,2
public class xxx {
private boolean detectBlueprintOnClassPathOrBlueprintXMLFiles() {
List<Dependency> deps = project.getCompileDependencies();
for (Dependency dep : deps) {
if ("org.apache.camel".equals(dep.getGroupId()) && "camel-blueprint".equals(dep.getArtifactId())) {


log.info("camel blueprint detected on classpath");
}
}
}

};