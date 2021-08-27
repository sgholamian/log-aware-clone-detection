//,temp,sample_5726.java,2,16,temp,sample_5727.java,2,16
//,2
public class xxx {
public void dummy_method(){
for (int i = 0; i < payloads.length; i++) {
final int finalI = i;
pool.execute(new Runnable() {
public void run() {
template.sendBody(payloads[finalI]);
}
});
}
latch.await();
long end = System.currentTimeMillis();


log.info("sending messages to took ms");
}

};