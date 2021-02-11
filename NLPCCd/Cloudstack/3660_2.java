//,temp,sample_6803.java,2,11,temp,sample_6293.java,2,11
//,2
public class xxx {
public void stop() {
_capacityScanScheduler.shutdownNow();
try {
_capacityScanScheduler.awaitTermination(1000, TimeUnit.MILLISECONDS);
} catch (InterruptedException e) {


log.info("ignored interupted while stopping systemvm load scanner");
}
}

};