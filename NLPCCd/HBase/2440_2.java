//,temp,sample_1140.java,2,18,temp,sample_2375.java,2,16
//,3
public class xxx {
private void wait(byte[] row, Table target, boolean isDeleted) throws Exception {
Get get = new Get(row);
for (int i = 0; i < NB_RETRIES; i++) {
if (i == NB_RETRIES - 1) {
fail("Waited too much time for replication. Row:" + Bytes.toString(row) + ". IsDeleteReplication:" + isDeleted);
}
Result res = target.get(get);
boolean sleep = isDeleted ? res.size() > 0 : res.isEmpty();
if (sleep) {


log.info("waiting for more time for replication row isdeletereplication");
}
}
}

};