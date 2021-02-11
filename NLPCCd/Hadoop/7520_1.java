//,temp,sample_4721.java,2,16,temp,sample_4728.java,2,17
//,3
public class xxx {
private void caseMultiFileTargetPresent(boolean sync) {
try {
Path listFile = new Path("/tmp/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
mkdirs(target.toString());
runTest(listFile, target, true, sync);
checkResult(listFile, 3);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};