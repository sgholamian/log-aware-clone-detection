//,temp,sample_3519.java,2,15,temp,sample_1766.java,2,15
//,2
public class xxx {
public void run() {
stoppedLatch = new CountDownLatch(1);
while (keepRunning) {
doRun();
if (keepRunning) {
cursor.close();
if (LOG.isDebugEnabled()) {


log.info("regenerating cursor with lastval waiting ms first");
}
}
}
}

};