//,temp,sample_1249.java,2,13,temp,sample_4715.java,2,15
//,3
public class xxx {
private void caseSingleFileMissingTarget(boolean sync) {
try {
addEntries(listFile, "singlefile1/file1");
createFiles("singlefile1/file1");
runTest(listFile, target, false, sync);
checkResult(target, 1);
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};