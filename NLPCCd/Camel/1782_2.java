//,temp,sample_588.java,2,11,temp,sample_584.java,2,11
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