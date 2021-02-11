//,temp,sample_4722.java,2,15,temp,sample_4724.java,2,16
//,3
public class xxx {
private void caseMultiFileTargetMissing(boolean sync) {
try {
Path listFile = new Path("/tmp/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
runTest(listFile, target, false, sync);
checkResult(listFile, 3);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};