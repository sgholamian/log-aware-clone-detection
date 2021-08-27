//,temp,sample_742.java,2,19,temp,sample_741.java,2,18
//,3
public class xxx {
public void dummy_method(){
catalog.setSuggestionStrategy(new LuceneSuggestionStrategy());
catalog.setVersionManager(new MavenVersionManager());
catalog.enableCache();
String detectedVersion = findCamelVersion(project);
if (detectedVersion != null) {
}
if (downloadVersion) {
String catalogVersion = catalog.getCatalogVersion();
String version = findCamelVersion(project);
if (version != null && !version.equals(catalogVersion)) {


log.info("downloading camel version");
}
}
}

};