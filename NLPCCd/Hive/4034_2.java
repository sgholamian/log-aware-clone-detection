//,temp,sample_2181.java,2,12,temp,sample_5547.java,2,15
//,3
public class xxx {
public void run() {
while (true) {
try {
doOneCleanupRound();
} catch (InterruptedException ex) {
Thread.currentThread().interrupt();
break;
} catch (Throwable t) {


log.info("cleanup has failed the thread will now exit");
}
}
}

};