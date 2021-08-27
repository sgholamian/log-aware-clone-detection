//,temp,sample_1803.java,2,12,temp,sample_1801.java,2,12
//,2
public class xxx {
protected void registerDataFormats(Bundle bundle, List<BaseService> resolvers) {
if (canSee(bundle, DataFormat.class)) {
Map<String, String> dataformats = new HashMap<String, String>();
for (Enumeration<?> e = bundle.getEntryPaths(META_INF_DATAFORMAT); e != null && e.hasMoreElements();) {
String path = (String) e.nextElement();


log.info("found entry in bundle");
}
}
}

};