//,temp,sample_2236.java,2,18,temp,sample_3179.java,2,18
//,3
public class xxx {
public void dummy_method(){
htable1.put(put);
Thread.sleep(SLEEP_TIME * NB_RETRIES);
admin.disablePeer("2");
utility2.startMiniHBaseCluster(1, 2);
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