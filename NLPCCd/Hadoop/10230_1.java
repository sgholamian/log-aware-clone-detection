//,temp,sample_1257.java,2,14,temp,sample_1255.java,2,14
//,3
public class xxx {
public void testMultiDirTargetPresent() {
try {
addEntries(listFile, "multifile", "singledir");
createFiles("multifile/file3", "multifile/file4", "multifile/file5");
mkdirs(target.toString(), root + "/singledir/dir1");
runTest(listFile, target, true, false);
checkResult(target, 2, "multifile/file3", "multifile/file4", "multifile/file5", "singledir/dir1");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};