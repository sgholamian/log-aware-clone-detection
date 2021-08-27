//,temp,sample_2124.java,2,16,temp,sample_2125.java,2,18
//,3
public class xxx {
public void dummy_method(){
Set<String> jars = new HashSet<String>();
jars.addAll(conf.getStringCollection("tmpjars"));
Map<String,String> packagedClasses = new HashMap<String,String>();
for (Class<?> clazz : classes) {
if (clazz == null) continue;
Path path = findOrCreateJar(clazz, localFs, packagedClasses);
if (path == null) {
continue;
}
if (!localFs.exists(path)) {


log.info("could not validate jar file for class");
}
}
}

};