//,temp,sample_8404.java,2,11,temp,sample_592.java,2,11
//,2
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