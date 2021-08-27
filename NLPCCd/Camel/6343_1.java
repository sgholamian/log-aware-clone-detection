//,temp,sample_7644.java,2,16,temp,sample_7643.java,2,13
//,3
public class xxx {
private boolean isStarterAllowed() {
for (String ignored : IGNORE_MODULES) {
if (ignored.equals(project.getArtifactId())) {
return false;
}
}
if (IGNORE_TEST_MODULES && (project.getArtifactId().startsWith("camel-test") || project.getArtifactId().startsWith("camel-testng"))) {
return false;
}
if (project.getPackaging() != null && !project.getPackaging().equals("jar")) {


log.info("ignored for wrong packaging");
}
}

};