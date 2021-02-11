//,temp,sample_1140.java,2,18,temp,sample_2375.java,2,16
//,3
public class xxx {
private void ensureRowExisted(Table target, byte[] row, byte[]... families) throws Exception {
for (byte[] fam : families) {
Get get = new Get(row);
get.addFamily(fam);
for (int i = 0; i < NB_RETRIES; i++) {
if (i == NB_RETRIES - 1) {
fail("Waited too much time for put replication");
}
Result res = target.get(get);
if (res.isEmpty()) {


log.info("row not available");
}
}
}
}

};