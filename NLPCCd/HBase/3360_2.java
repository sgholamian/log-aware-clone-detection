//,temp,sample_1638.java,2,19,temp,sample_3187.java,2,18
//,3
public class xxx {
public void dummy_method(){
htable1.put(put);
Get get = new Get(rowKey);
for (int i = 0; i < NB_RETRIES; i++) {
if (i == NB_RETRIES - 1) {
break;
}
Result res = htable2.get(get);
if (res.size() >= 1) {
fail("Not supposed to be replicated");
} else {


log.info("row not replicated let s wait a bit more");
}
}
}

};