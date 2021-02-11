//,temp,sample_4725.java,2,16,temp,sample_1256.java,2,13
//,3
public class xxx {
private void caseMultiFileTargetMissing(boolean sync) {
try {
addEntries(listFile, "multifile/file3", "multifile/file4", "multifile/file5");
createFiles("multifile/file3", "multifile/file4", "multifile/file5");
runTest(listFile, target, false, sync);
checkResult(target, 3, "file3", "file4", "file5");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};