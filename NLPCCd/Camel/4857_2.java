//,temp,sample_1803.java,2,12,temp,sample_1801.java,2,12
//,2
public class xxx {
protected void registerLanguages(Bundle bundle, List<BaseService> resolvers) {
if (canSee(bundle, Language.class)) {
Map<String, String> languages = new HashMap<String, String>();
for (Enumeration<?> e = bundle.getEntryPaths(META_INF_LANGUAGE); e != null && e.hasMoreElements();) {
String path = (String) e.nextElement();


log.info("found entry in bundle");
}
}
}

};