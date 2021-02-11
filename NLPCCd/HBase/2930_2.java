//,temp,sample_1438.java,2,10,temp,sample_66.java,2,13
//,3
public class xxx {
private void cleanup(String stagingDir, Table table) {
fsDelegationToken.releaseDelegationToken();
if (stagingDir != null) {
try {
sinkFs.delete(new Path(stagingDir), true);
} catch (IOException e) {


log.info("failed to delete the staging directory");
}
}
}

};