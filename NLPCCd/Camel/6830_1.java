//,temp,sample_3655.java,2,14,temp,sample_3656.java,2,17
//,3
public class xxx {
private void doForceCompletionOnStop() {
int expected = forceCompletionOfAllGroups();
StopWatch watch = new StopWatch();
while (inProgressCompleteExchanges.size() > 0) {
try {
Thread.sleep(100);
} catch (InterruptedException e) {


log.info("interrupted while waiting for inflight exchanges to complete");
}
}
}

};