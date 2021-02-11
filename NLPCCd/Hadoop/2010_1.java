//,temp,sample_4729.java,2,17,temp,sample_4722.java,2,15
//,3
public class xxx {
public void dummy_method(){
try {
Path listFile = new Path("/tmp1/listing");
Path target = new Path("/tmp/target");
addEntries(listFile, "/tmp/*/*");
createFiles("/tmp/Umultifile/Ufile3", "/tmp/Umultifile/Ufile4", "/tmp/Umultifile/Ufile5");
createFiles("/tmp/Usingledir1/Udir3/Ufile7", "/tmp/Usingledir1/Udir3/Ufile8", "/tmp/Usingledir1/Udir3/Ufile9");
mkdirs(target.toString());
runTest(listFile, target, true);
checkResult(listFile, 6);
} catch (IOException e) {


log.info("exception encountered while testing build listing");
}
}

};