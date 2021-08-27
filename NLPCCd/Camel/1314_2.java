//,temp,sample_596.java,2,17,temp,sample_8406.java,2,11
//,3
public class xxx {
private void executeLanguage() throws MojoExecutionException, MojoFailureException {
List<String> languageNames = findLanguageNames();
final Set<File> jsonFiles = new TreeSet<File>();
PackageHelper.findJsonFiles(buildDir, jsonFiles, new PackageHelper.CamelComponentsModelFilter());
if (!languageNames.isEmpty()) {


log.info("found languages");
}
}

};