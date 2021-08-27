//,temp,sample_1960.java,2,11,temp,sample_6082.java,2,11
//,3
public class xxx {
private boolean sleep() {
try {
Thread.sleep(checkInterval);
return false;
} catch (InterruptedException e) {


log.info("sleep interrupted while waiting for exclusive read lock so breaking out");
}
}

};