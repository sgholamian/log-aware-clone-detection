//,temp,sample_3189.java,2,18,temp,sample_3184.java,2,18
//,3
public class xxx {
public void dummy_method(){
byte[] rowkey = Bytes.toBytes("disable enable");
Put put = new Put(rowkey);
put.addColumn(famName, row, row);
htable1.put(put);
Get get = new Get(rowkey);
for (int i = 0; i < NB_RETRIES; i++) {
Result res = htable2.get(get);
if (res.size() >= 1) {
fail("Replication wasn't disabled");
} else {


log.info("row not replicated let s wait a bit more");
}
}
}

};