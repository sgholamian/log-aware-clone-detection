//,temp,sample_5705.java,2,10,temp,sample_5706.java,2,13
//,3
public class xxx {
public void run() {
try {
Thread.sleep(sleepBeforeFailover + (long) (ThreadLocalRandom.current().nextFloat() * sleepBeforeFailover));
} catch (InterruptedException e) {


log.info("interrupted while waiting before transferring a queue");
}
}

};