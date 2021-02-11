//,temp,sample_4720.java,2,16,temp,sample_4728.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
Path listFile = new Path("/tmp1/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/*/*");
createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
createFiles("/tmp/singledir1/dir3/file7", "/tmp/singledir1/dir3/file8", "/tmp/singledir1/dir3/file9");
mkdirs(target.toString());
runTest(listFile, target, true);
checkResult(listFile, 6);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};