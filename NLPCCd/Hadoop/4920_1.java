//,temp,sample_1260.java,2,14,temp,sample_1251.java,2,14
//,3
public class xxx {
public void testUpdateMultiDirTargetMissing() {
try {
addEntries(listFile, "multifile", "singledir");
createFiles("multifile/file3", "multifile/file4", "multifile/file5");
mkdirs(root + "/singledir/dir1");
runTest(listFile, target, false, true);
checkResult(target, 4, "file3", "file4", "file5", "dir1");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};