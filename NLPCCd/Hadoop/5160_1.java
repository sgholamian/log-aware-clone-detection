//,temp,sample_4719.java,2,16,temp,sample_4726.java,2,16
//,3
public class xxx {
public void testSingleDirTargetPresent() {
try {
Path listFile = new Path("/tmp/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/singledir");
mkdirs("/tmp/singledir/dir1");
mkdirs(target.toString());
runTest(listFile, target, true);
checkResult(listFile, 1);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};