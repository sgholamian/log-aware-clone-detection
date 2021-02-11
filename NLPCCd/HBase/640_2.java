//,temp,sample_3181.java,2,18,temp,sample_3179.java,2,18
//,3
public class xxx {
public void dummy_method(){
put.addColumn(famName, row, t + 2, v3);
htable1.put(put);
Get get = new Get(row);
get.readAllVersions();
for (int i = 0; i < NB_RETRIES; i++) {
if (i == NB_RETRIES - 1) {
fail("Waited too much time for put replication");
}
Result res = htable2.get(get);
if (res.size() < 3) {


log.info("rows not available");
}
}
}

};