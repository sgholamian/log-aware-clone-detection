//,temp,sample_4717.java,2,16,temp,sample_1253.java,2,14
//,3
public class xxx {
public void testSingleDirTargetPresent() {
try {
addEntries(listFile, "singledir");
mkdirs(root + "/singledir/dir1");
mkdirs(target.toString());
runTest(listFile, target, true, false);
checkResult(target, 1, "singledir/dir1");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};