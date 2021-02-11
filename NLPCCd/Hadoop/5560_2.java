//,temp,sample_2004.java,2,16,temp,sample_2003.java,2,16
//,2
public class xxx {
public void dummy_method(){
final Path sub2 = new Path(sub1, "sub2");
final Path sub2file1 = new Path(sub2, "sub2file1");
final Path sub2file2 = new Path(sub2, "sub2file2");
final String sub1snap1 = "sub1snap1";
hdfs.mkdirs(sub1);
hdfs.mkdirs(sub2);
DFSTestUtil.createFile(hdfs, sub2file1, BLOCKSIZE, REPL, SEED);
SnapshotTestHelper.createSnapshot(hdfs, sub1, sub1snap1);
hdfs.rename(sub2file1, sub2file2);
SnapshotDiffReport diffReport = hdfs.getSnapshotDiffReport(sub1, sub1snap1, "");


log.info("difflist is diffreport tostring");
}

};