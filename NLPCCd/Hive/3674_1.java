//,temp,sample_3078.java,2,11,temp,sample_3079.java,2,15
//,3
public class xxx {
public void loadDependentJars(Configuration conf) {
List<Class<?>> classesToLoad = new ArrayList<>(Arrays.asList(Tracer.class, Fate.class, Connector.class, Main.class, ZooKeeper.class, AccumuloStorageHandler.class));
try {
classesToLoad.add(Class.forName("org.apache.htrace.Trace"));
} catch (Exception e) {


log.info("failed to load class for htrace jar trying to continue");
}
}

};