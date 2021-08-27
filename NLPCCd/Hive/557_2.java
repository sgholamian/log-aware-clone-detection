//,temp,sample_2178.java,2,12,temp,sample_5118.java,2,15
//,3
public class xxx {
public void killFragment(String fragmentId) {
synchronized (lock) {
TaskWrapper taskWrapper = knownTasks.remove(fragmentId);
if (taskWrapper != null) {
if (taskWrapper.isInWaitQueue()) {
if (LOG.isDebugEnabled()) {


log.info("removing from waitqueue");
}
}
}
}
}

};