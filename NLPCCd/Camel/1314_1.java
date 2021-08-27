//,temp,sample_596.java,2,17,temp,sample_8406.java,2,11
//,3
public class xxx {
public void dummy_method(){
if (!currentDir.endsWith("camel-core")) {
return;
}
final Set<File> jsonFiles = new TreeSet<File>();
File coreDir = new File(".");
if (coreDir.isDirectory()) {
File target = new File(coreDir, "target/classes/org/apache/camel/model");
PackageHelper.findJsonFiles(target, jsonFiles, new PackageHelper.CamelComponentsModelFilter());
}
if (!jsonFiles.isEmpty()) {


log.info("found eips");
}
}

};