//,temp,sample_5269.java,2,14,temp,sample_685.java,2,12
//,3
public class xxx {
private void dropSessionPaths(Configuration conf) throws IOException {
if (hdfsSessionPath != null) {
if (hdfsSessionPathLockFile != null) {
try {
hdfsSessionPathLockFile.close();
} catch (IOException e) {


log.info("failed while closing remotefssessionlockfile");
}
}
}
}

};