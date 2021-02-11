//,temp,sample_4716.java,2,15,temp,sample_4715.java,2,15
//,3
public class xxx {
private void caseSingleFileTargetFile(boolean sync) {
try {
Path listFile = new Path("/tmp/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/singlefile1/file1");
createFiles("/tmp/singlefile1/file1", target.toString());
runTest(listFile, target, false, sync);
checkResult(listFile, 0);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};