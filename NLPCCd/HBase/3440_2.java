//,temp,sample_3189.java,2,18,temp,sample_4032.java,2,18
//,3
public class xxx {
public void dummy_method(){
put.addColumn(famName, row, row);
htable1 = utility1.getConnection().getTable(tableName);
htable1.put(put);
Get get = new Get(row);
for (int i = 0; i < NB_RETRIES; i++) {
if (i == NB_RETRIES - 1) {
fail("Waited too much time for put replication");
}
Result res = htable2.get(get);
if (res.isEmpty()) {


log.info("row not available");
}
}
}

};