//,temp,sample_7809.java,2,17,temp,sample_7808.java,2,17
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
String changed = templateLanguages(languages, count, deprecated);
boolean updated = updateLanguages(file, changed);
if (updated) {


log.info("updated readme adoc file");
}
}

};