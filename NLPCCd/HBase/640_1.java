//,temp,sample_3181.java,2,18,temp,sample_3179.java,2,18
//,3
public class xxx {
public void dummy_method(){
d = new Delete(row);
d.addColumns(famName, row, t + 2);
htable1.delete(d);
get = new Get(row);
for (int i = 0; i < NB_RETRIES; i++) {
if (i == NB_RETRIES - 1) {
fail("Waited too much time for del replication");
}
Result res = htable2.get(get);
if (res.size() >= 1) {


log.info("rows not deleted");
}
}
}

};