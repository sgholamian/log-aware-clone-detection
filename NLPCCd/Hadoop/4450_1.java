//,temp,sample_4178.java,2,12,temp,sample_5768.java,2,11
//,3
public class xxx {
private void deleteTargetTmpDir(DistributedFileSystem targetFs, Path tmpDir) {
try {
if (tmpDir != null) {
targetFs.delete(tmpDir, true);
}
} catch (IOException e) {


log.info("unable to cleanup tmp dir");
}
}

};