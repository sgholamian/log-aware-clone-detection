//,temp,sample_6460.java,2,11,temp,sample_6459.java,2,11
//,2
public class xxx {
private boolean detectCDIOnClassPath() {
List<Dependency> deps = project.getCompileDependencies();
for (Dependency dep : deps) {
if ("org.apache.camel".equals(dep.getGroupId()) && "camel-cdi".equals(dep.getArtifactId())) {


log.info("camel cdi detected on classpath");
}
}
}

};