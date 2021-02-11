//,temp,sample_9131.java,2,12,temp,sample_9132.java,2,13
//,3
public class xxx {
public boolean createVmdataFiles(final String vmName, final List<String[]> vmDataList, final String configDriveLabel) {
final String isoPath = "/tmp/"+vmName+"/configDrive/";
final String configDriveName = "cloudstack/";
try {
deleteLocalFolder("/tmp/"+isoPath);
} catch (final IOException e) {


log.info("failed to delete the exiting config drive for vm");
}
}

};