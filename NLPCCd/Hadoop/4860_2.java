//,temp,sample_4722.java,2,15,temp,sample_4724.java,2,16
//,3
public class xxx {
public void testUpdateMultiDirTargetPresent() {
try {
Path listFile = new Path("/tmp/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/Umultifile", "/tmp/Usingledir");
createFiles("/tmp/Umultifile/Ufile3", "/tmp/Umultifile/Ufile4", "/tmp/Umultifile/Ufile5");
mkdirs(target.toString(), "/tmp/Usingledir/Udir1");
runTest(listFile, target, true);
checkResult(listFile, 4);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};