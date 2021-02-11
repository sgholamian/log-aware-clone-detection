//,temp,sample_6803.java,2,11,temp,sample_6293.java,2,11
//,2
public class xxx {
public boolean stop() {
_hostScanScheduler.shutdownNow();
try {
_hostScanScheduler.awaitTermination(3000, TimeUnit.MILLISECONDS);
} catch (InterruptedException e) {


log.info("ignored interupted while stopping");
}
}

};