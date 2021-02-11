//,temp,sample_1638.java,2,19,temp,sample_3187.java,2,18
//,3
public class xxx {
public void dummy_method(){
Get get = new Get(row);
get.addFamily(fam);
for (int i = 0; i < NB_RETRIES; i++) {
if (i==NB_RETRIES-1) {
fail("Waited too much time for del replication");
}
boolean removedFromAll = true;
for (Table target : targets) {
Result res = target.get(get);
if (res.size() >= 1) {


log.info("row not deleted");
}
}
}
}

};