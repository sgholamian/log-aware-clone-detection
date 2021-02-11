//,temp,sample_4718.java,2,15,temp,sample_4723.java,2,16
//,3
public class xxx {
private void caseSingleDirTargetMissing(boolean sync) {
try {
Path listFile = new Path("/tmp/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/singledir");
mkdirs("/tmp/singledir/dir1");
runTest(listFile, target, false, sync);
checkResult(listFile, 1);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};