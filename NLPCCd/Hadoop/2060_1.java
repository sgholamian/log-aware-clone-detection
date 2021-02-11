//,temp,sample_1266.java,2,15,temp,sample_4728.java,2,17
//,3
public class xxx {
public void testUpdateGlobTargetMissingMultiLevel() {
try {
Path listFile = new Path("target/tmp1/listing").makeQualified(fs.getUri(), fs.getWorkingDirectory());
addEntries(listFile, "*/*");
createFiles("multifile/file3", "multifile/file4", "multifile/file5");
createFiles("singledir1/dir3/file7", "singledir1/dir3/file8", "singledir1/dir3/file9");
runTest(listFile, target, false, true);
checkResult(target, 6, "file3", "file4", "file5", "file7", "file8", "file9");
} catch (IOException e) {


log.info("exception encountered while running distcp");
}
}

};