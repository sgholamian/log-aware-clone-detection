//,temp,sample_1800.java,2,12,temp,sample_1538.java,2,10
//,3
public class xxx {
protected void registerComponents(Bundle bundle, List<BaseService> resolvers) {
if (canSee(bundle, Component.class)) {
Map<String, String> components = new HashMap<String, String>();
for (Enumeration<?> e = bundle.getEntryPaths(META_INF_COMPONENT); e != null && e.hasMoreElements();) {
String path = (String) e.nextElement();


log.info("found entry in bundle");
}
}
}

};