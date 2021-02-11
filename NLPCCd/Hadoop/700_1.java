//,temp,sample_1254.java,2,14,temp,sample_4717.java,2,16
//,3
public class xxx {
public void testUpdateSingleDirTargetPresent() {
try {
addEntries(listFile, "Usingledir");
mkdirs(root + "/Usingledir/Udir1");
mkdirs(target.toString());
runTest(listFile, target, true, true);
checkResult(target, 1, "Udir1");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};