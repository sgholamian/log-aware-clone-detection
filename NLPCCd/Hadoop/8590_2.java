//,temp,sample_5678.java,2,10,temp,sample_7262.java,2,12
//,3
public class xxx {
private static void sigKillInCurrentThread(String pid, boolean isProcessGroup, long sleepTimeBeforeSigKill) {
if (isProcessGroup || ProcessTree.isAlive(pid)) {
try {
Thread.sleep(sleepTimeBeforeSigKill);
} catch (InterruptedException i) {


log.info("thread sleep is interrupted");
}
}
}

};