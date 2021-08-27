//,temp,sample_6449.java,2,18,temp,sample_51.java,2,18
//,2
public class xxx {
public void dummy_method(){
bootstrapThread.start();
joinNonDaemonThreads(threadGroup);
if (keepAlive) {
waitFor(0);
}
if (cleanupDaemonThreads) {
terminateThreads(threadGroup);
try {
threadGroup.destroy();
} catch (IllegalThreadStateException e) {


log.info("couldn t destroy thread group");
}
}
}

};