//,temp,sample_5715.java,2,12,temp,sample_8190.java,2,12
//,2
public class xxx {
private void checkVersion() throws IOException {
Version loadedVersion = loadVersion();
if (loadedVersion.equals(getCurrentVersion())) {
return;
}
if (loadedVersion.isCompatibleTo(getCurrentVersion())) {


log.info("storing timeline store version info");
}
}

};