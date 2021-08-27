//,temp,sample_2567.java,2,14,temp,sample_2568.java,2,15
//,2
public class xxx {
private void loadImplementationsInJar(PackageScanFilter test, String parent, InputStream stream, String urlPath, Set<Class<?>> classes, Map<String, List<String>> jarCache) {
ObjectHelper.notNull(classes, "classes");
List<String> entries = jarCache != null ? jarCache.get(urlPath) : null;
if (entries == null) {
entries = doLoadJarClassEntries(stream, urlPath);
if (jarCache != null) {
jarCache.put(urlPath, entries);
}
} else {


log.info("using cached jar with entries");
}
}

};