//,temp,sample_3282.java,2,12,temp,sample_5369.java,2,12
//,2
public class xxx {
private void checkVersion() throws IOException {
Version loadedVersion = loadVersion();
if (loadedVersion.equals(getCurrentVersion())) {
return;
}
if (loadedVersion.isCompatibleTo(getCurrentVersion())) {


log.info("storing timeline state store version info");
}
}

};