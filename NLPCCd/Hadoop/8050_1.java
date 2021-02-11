//,temp,sample_2670.java,2,15,temp,sample_7119.java,2,16
//,3
public class xxx {
public boolean isRunning() {
if (super.isRunning()) {
return true;
}
try {
Thread.sleep(10);
} catch (InterruptedException ie) {
Thread.currentThread().interrupt();
}
boolean runState = super.isRunning();


log.info("httpserver acceptor isrunning is");
}

};