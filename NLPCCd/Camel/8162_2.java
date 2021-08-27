//,temp,sample_3042.java,2,11,temp,sample_3043.java,2,11
//,2
public class xxx {
private void refreshStatusBecomingLeader() {
long delay = this.lockConfiguration.getLeaseDurationMillis();
try {
Thread.sleep(delay);
} catch (InterruptedException e) {
}


log.info("current pod is becoming the new leader now");
}

};