//,temp,sample_4718.java,2,15,temp,sample_4723.java,2,16
//,3
public class xxx {
public void testMultiDirTargetPresent() {
try {
Path listFile = new Path("/tmp/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/multifile", "/tmp/singledir");
createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
mkdirs(target.toString(), "/tmp/singledir/dir1");
runTest(listFile, target, true);
checkResult(listFile, 4);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};