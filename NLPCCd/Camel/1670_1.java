//,temp,sample_7805.java,2,17,temp,sample_7806.java,2,17
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
String changed = templateDataFormats(dataFormats, count, deprecated);
boolean updated = updateDataFormats(file, changed);
if (updated) {


log.info("updated readme adoc file");
}
}

};