//,temp,sample_3859.java,2,10,temp,sample_2067.java,2,10
//,2
public class xxx {
private static URL checkConfigFile(File f) {
try {
return (f.exists() && f.isFile()) ? f.toURI().toURL() : null;
} catch (Throwable e) {


log.info("error looking for config");
}
}

};