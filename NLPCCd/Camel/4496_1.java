//,temp,sample_2494.java,2,14,temp,sample_4356.java,2,14
//,2
public class xxx {
protected void scheduleDelayedStart() throws Exception {
Runnable startRunnable = new Runnable() {
public void run() {
try {
doStart();
} catch (Exception e) {
}
}
};


log.info("delaying jmx consumer startup for endpoint trying again in seconds");
}

};