//,temp,sample_4729.java,2,17,temp,sample_1259.java,2,14
//,3
public class xxx {
public void testMultiDirTargetMissing() {
try {
addEntries(listFile, "multifile", "singledir");
createFiles("multifile/file3", "multifile/file4", "multifile/file5");
mkdirs(root + "/singledir/dir1");
runTest(listFile, target, false, false);
checkResult(target, 2, "multifile/file3", "multifile/file4", "multifile/file5", "singledir/dir1");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};