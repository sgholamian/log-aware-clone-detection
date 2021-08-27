//,temp,sample_6985.java,2,18,temp,sample_4924.java,2,13
//,3
public class xxx {
protected void registerScriptEngines(Bundle bundle, List<BundleScriptEngineResolver> resolvers) {
try {
for (Enumeration<?> e = bundle.adapt(BundleWiring.class).getClassLoader().getResources(META_INF_SERVICES_DIR + "/" + SCRIPT_ENGINE_SERVICE_FILE); e != null && e.hasMoreElements();) {
URL configURL = (URL) e.nextElement();
resolvers.add(new BundleScriptEngineResolver(bundle, configURL));
}
} catch (IOException e) {


log.info("error loading script engine factory");
}
}

};