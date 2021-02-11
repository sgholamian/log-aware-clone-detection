//,temp,sample_1257.java,2,14,temp,sample_1255.java,2,14
//,3
public class xxx {
private void caseMultiFileTargetPresent(boolean sync) {
try {
addEntries(listFile, "multifile/file3", "multifile/file4", "multifile/file5");
createFiles("multifile/file3", "multifile/file4", "multifile/file5");
mkdirs(target.toString());
runTest(listFile, target, true, sync);
checkResult(target, 3, "file3", "file4", "file5");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};