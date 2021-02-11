//,temp,sample_1251.java,2,14,temp,sample_4726.java,2,16
//,3
public class xxx {
private void caseGlobTargetMissingSingleLevel(boolean sync) {
try {
Path listFile = new Path("/tmp1/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/*");
createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
createFiles("/tmp/singledir/dir2/file6");
runTest(listFile, target, sync);
checkResult(listFile, 5);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};