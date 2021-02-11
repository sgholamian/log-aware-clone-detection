//,temp,sample_1260.java,2,14,temp,sample_1251.java,2,14
//,3
public class xxx {
private void caseSingleFileTargetDir(boolean sync) {
try {
addEntries(listFile, "singlefile2/file2");
createFiles("singlefile2/file2");
mkdirs(target.toString());
runTest(listFile, target, true, sync);
checkResult(target, 1, "file2");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};