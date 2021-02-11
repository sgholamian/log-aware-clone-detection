//,temp,sample_3163.java,2,12,temp,sample_5937.java,2,13
//,3
public class xxx {
protected void serviceStop() throws Exception {
stopped = true;
synchronized(lock) {
if (eventHandlingThread != null) {
eventHandlingThread.interrupt();
} else {


log.info("null event handling thread");
}
}
}

};