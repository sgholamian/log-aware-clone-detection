//,temp,sample_7800.java,2,17,temp,sample_7799.java,2,17
//,3
public class xxx {
public void dummy_method(){
File file;
if (coreOnly) {
file = new File(readmeCoreDir, "readme.adoc");
} else {
file = new File(readmeComponentsDir, "readme.adoc");
}
boolean exists = file.exists();
String changed = templateComponents(components, count, deprecated);
boolean updated = updateComponents(file, changed);
if (updated) {


log.info("updated readme adoc file");
}
}

};