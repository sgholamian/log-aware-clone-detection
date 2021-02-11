//,temp,sample_1250.java,2,13,temp,sample_1259.java,2,14
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