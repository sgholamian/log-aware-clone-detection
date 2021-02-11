//,temp,sample_4720.java,2,16,temp,sample_4728.java,2,17
//,3
public class xxx {
public void testUpdateSingleDirTargetPresent() {
try {
Path listFile = new Path("/tmp/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/Usingledir");
mkdirs("/tmp/Usingledir/Udir1");
mkdirs(target.toString());
runTest(listFile, target, true, true);
checkResult(listFile, 1);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};