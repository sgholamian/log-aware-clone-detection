//,temp,sample_3483.java,2,19,temp,sample_3482.java,2,19
//,3
public class xxx {
public void dummy_method(){
try {
if (isPopulatingReplQueues()) {
computeDatanodeWork();
processPendingReplications();
rescanPostponedMisreplicatedBlocks();
}
Thread.sleep(replicationRecheckInterval);
} catch (Throwable t) {
if (!namesystem.isRunning()) {
if (!(t instanceof InterruptedException)) {


log.info("replicationmonitor received an exception while shutting down");
}
}
}
}

};