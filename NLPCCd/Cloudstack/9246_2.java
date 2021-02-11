//,temp,sample_4222.java,2,15,temp,sample_4221.java,2,13
//,3
public class xxx {
public void run() {
while (!_done) {
try {
Thread.sleep(1000);
updateLeaseProgress(_percent);
} catch (InterruptedException e) {


log.info("progressreporter is interrupted quiting");
}
}
}

};