//,temp,sample_3184.java,2,18,temp,sample_255.java,2,18
//,3
public class xxx {
public void dummy_method(){
Put put = new Put(row);
put.addColumn(familyname, row, row);
lHtable1.put(put);
Get get = new Get(row);
for (int i = 0; i < NB_RETRIES; i++) {
if (i == NB_RETRIES - 1) {
fail("Waited too much time for put replication");
}
Result res = lHtable2.get(get);
if (res.isEmpty()) {


log.info("row not available");
}
}
}

};