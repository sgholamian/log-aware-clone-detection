//,temp,sample_3196.java,2,18,temp,sample_3184.java,2,18
//,3
public class xxx {
public void dummy_method(){
wal.sync();
Get get = new Get(rowName);
for (int i = 0; i < NB_RETRIES; i++) {
if (i == NB_RETRIES - 1) {
break;
}
Result res = htable2.get(get);
if (res.size() >= 1) {
fail("Not supposed to be replicated for " + Bytes.toString(res.getRow()));
} else {


log.info("row not replicated let s wait a bit more");
}
}
}

};