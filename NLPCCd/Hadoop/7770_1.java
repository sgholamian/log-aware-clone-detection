//,temp,sample_1264.java,2,15,temp,sample_4717.java,2,16
//,3
public class xxx {
public void testUpdateGlobTargetMissingSingleLevel() {
try {
Path listFile = new Path("target/tmp1/listing").makeQualified(fs.getUri(), fs.getWorkingDirectory());
addEntries(listFile, "*");
createFiles("multifile/file3", "multifile/file4", "multifile/file5");
createFiles("singledir/dir2/file6");
runTest(listFile, target, false, true);
checkResult(target, 4, "file3", "file4", "file5", "dir2/file6");
} catch (IOException e) {


log.info("exception encountered while running distcp");
}
}

};