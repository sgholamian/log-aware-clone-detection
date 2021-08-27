//,temp,sample_2239.java,2,11,temp,sample_4975.java,2,11
//,2
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