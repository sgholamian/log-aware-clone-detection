//,temp,sample_1252.java,2,13,temp,sample_1259.java,2,14
//,3
public class xxx {
private void caseSingleDirTargetMissing(boolean sync) {
try {
addEntries(listFile, "singledir");
mkdirs(root + "/singledir/dir1");
runTest(listFile, target, false, sync);
checkResult(target, 1, "dir1");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};