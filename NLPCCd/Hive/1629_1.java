//,temp,sample_2124.java,2,16,temp,sample_2125.java,2,18
//,3
public class xxx {
public static void addDependencyJars(Configuration conf, List<Class<?>> classes) throws IOException {
FileSystem localFs = FileSystem.getLocal(conf);
Set<String> jars = new HashSet<String>();
jars.addAll(conf.getStringCollection("tmpjars"));
Map<String,String> packagedClasses = new HashMap<String,String>();
for (Class<?> clazz : classes) {
if (clazz == null) continue;
Path path = findOrCreateJar(clazz, localFs, packagedClasses);
if (path == null) {


log.info("could not find jar for class in order to ship it to the cluster");
}
}
}

};