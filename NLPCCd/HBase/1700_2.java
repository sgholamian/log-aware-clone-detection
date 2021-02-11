//,temp,sample_3187.java,2,18,temp,sample_3881.java,2,19
//,3
public class xxx {
public void dummy_method(){
source.put(put);
Get get = new Get(row);
for (int i = 0; i < NB_RETRIES; i++) {
if (i==NB_RETRIES-1) {
fail("Waited too much time for put replication");
}
boolean replicatedToAll = true;
for (Table target : targets) {
Result res = target.get(get);
if (res.isEmpty()) {


log.info("row not available");
}
}
}
}

};