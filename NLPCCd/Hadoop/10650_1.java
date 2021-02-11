//,temp,sample_8412.java,2,16,temp,sample_8414.java,2,16
//,2
public class xxx {
public void dummy_method(){
SnapshotTestHelper.createSnapshot(hdfs, root, "s2");
printAtime(filePreSS, root, "s2");
printAtime(dirPreSS, root, "s2");
printAtime(filePostSS, root, "s2");
printAtime(dirPostSS, root, "s2");
Thread.sleep(3000);
now = Time.now();
hdfs.setReplication(filePostSS, (short) (REPLICATION - 1));
hdfs.setTimes(filePostSS, -1, now);
SnapshotTestHelper.createSnapshot(hdfs, root, "s3");


log.info("s0 s1");
}

};