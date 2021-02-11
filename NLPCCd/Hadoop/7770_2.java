//,temp,sample_1264.java,2,15,temp,sample_4717.java,2,16
//,3
public class xxx {
private void caseSingleFileTargetDir(boolean sync) {
try {
Path listFile = new Path("/tmp/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/singlefile2/file2");
createFiles("/tmp/singlefile2/file2");
mkdirs(target.toString());
runTest(listFile, target, true, sync);
checkResult(listFile, 1);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};