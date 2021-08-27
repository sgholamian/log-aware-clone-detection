//,temp,sample_1176.java,2,18,temp,sample_1174.java,2,18
//,2
public class xxx {
public void dummy_method(){
for (int i = 0; i < stat.length; i++) {
if (stat[i].getPath().getName().equals("base_0000004")) {
sawNewBase = true;
FileStatus[] buckets = fs.listStatus(stat[i].getPath());
Assert.assertEquals(2, buckets.length);
Assert.assertTrue(buckets[0].getPath().getName().matches("bucket_0000[01]"));
Assert.assertTrue(buckets[1].getPath().getName().matches("bucket_0000[01]"));
Assert.assertEquals(104L, buckets[0].getLen());
Assert.assertEquals(104L, buckets[1].getLen());
} else {


log.info("this is not the file you are looking for");
}
}
}

};