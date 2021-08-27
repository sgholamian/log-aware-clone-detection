//,temp,sample_8404.java,2,11,temp,sample_592.java,2,11
//,2
public class xxx {
private void executeComponent() throws MojoExecutionException, MojoFailureException {
List<String> componentNames = findComponentNames();
final Set<File> jsonFiles = new TreeSet<File>();
PackageHelper.findJsonFiles(buildDir, jsonFiles, new PackageHelper.CamelComponentsModelFilter());
if (!componentNames.isEmpty()) {


log.info("found components");
}
}

};