//,temp,sample_5387.java,2,14,temp,sample_5388.java,2,18
//,3
public class xxx {
public void dummy_method(){
TTransportException exception = null;
for (int i = 0 ; i < retries; i++) {
try {
return connect(conf);
} catch (TTransportException e) {
exception = e;
}
try {
Thread.sleep(retryDelaySeconds * 1000);
} catch (InterruptedException e) {


log.info("Interrupted");
}
}
}

};