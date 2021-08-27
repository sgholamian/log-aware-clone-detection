//,temp,sample_1180.java,2,18,temp,sample_1165.java,2,17
//,3
public class xxx {
public void dummy_method(){
FileStatus[] stat = fs.listStatus(new Path(t.getSd().getLocation()));
boolean sawNewDelta = false;
for (int i = 0; i < stat.length; i++) {
if (stat[i].getPath().getName().equals(makeDeltaDirNameCompacted(21, 24))) {
sawNewDelta = true;
FileStatus[] buckets = fs.listStatus(stat[i].getPath());
Assert.assertEquals(2, buckets.length);
Assert.assertTrue(buckets[0].getPath().getName().matches("bucket_0000[01]"));
Assert.assertTrue(buckets[1].getPath().getName().matches("bucket_0000[01]"));
} else {


log.info("this is not the file you are looking for");
}
}
}

};