//,temp,sample_1263.java,2,15,temp,sample_1261.java,2,14
//,3
public class xxx {
public void testGlobTargetMissingSingleLevel() {
try {
Path listFile = new Path("target/tmp1/listing").makeQualified(fs.getUri(), fs.getWorkingDirectory());
addEntries(listFile, "*");
createFiles("multifile/file3", "multifile/file4", "multifile/file5");
createFiles("singledir/dir2/file6");
runTest(listFile, target, false, false);
checkResult(target, 2, "multifile/file3", "multifile/file4", "multifile/file5", "singledir/dir2/file6");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};