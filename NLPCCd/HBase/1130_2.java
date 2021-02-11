//,temp,sample_5705.java,2,10,temp,sample_5706.java,2,13
//,3
public class xxx {
public void run() {
try {
Thread.sleep(sleepBeforeFailover + (long) (ThreadLocalRandom.current().nextFloat() * sleepBeforeFailover));
} catch (InterruptedException e) {
Thread.currentThread().interrupt();
}
if (server.isStopped()) {


log.info("not transferring queue since we are shutting down");
}
}

};