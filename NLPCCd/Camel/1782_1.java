//,temp,sample_588.java,2,11,temp,sample_584.java,2,11
//,2
public class xxx {
private void executeDataFormat() throws MojoExecutionException, MojoFailureException {
List<String> dataFormatNames = findDataFormatNames();
final Set<File> jsonFiles = new TreeSet<File>();
PackageHelper.findJsonFiles(buildDir, jsonFiles, new PackageHelper.CamelComponentsModelFilter());
if (!dataFormatNames.isEmpty()) {


log.info("found dataformats");
}
}

};