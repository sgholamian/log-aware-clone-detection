//,temp,sample_4718.java,2,15,temp,sample_1250.java,2,13
//,3
public class xxx {
private void caseSingleFileTargetFile(boolean sync) {
try {
addEntries(listFile, "singlefile1/file1");
createFiles("singlefile1/file1", "target");
runTest(listFile, target, false, sync);
checkResult(target, 1);
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};