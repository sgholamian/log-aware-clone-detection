//,temp,sample_4222.java,2,15,temp,sample_4223.java,2,15
//,2
public class xxx {
public void run() {
while (!_done) {
try {
Thread.sleep(1000);
updateLeaseProgress(_percent);
} catch (InterruptedException e) {
break;
} catch (Exception e) {


log.info("unexpected exception");
}
}
}

};