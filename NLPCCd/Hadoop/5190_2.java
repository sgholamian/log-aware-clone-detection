//,temp,sample_1263.java,2,15,temp,sample_1261.java,2,14
//,3
public class xxx {
public void testDeleteMissingInDestination() {
try {
addEntries(listFile, "srcdir");
createFiles("srcdir/file1", "dstdir/file1", "dstdir/file2");
Path target = new Path(root + "/dstdir");
runTest(listFile, target, false, true, true, false);
checkResult(target, 1, "file1");
} catch (IOException e) {


log.info("exception encountered while running distcp");
}
}

};