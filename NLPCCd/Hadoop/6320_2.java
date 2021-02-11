//,temp,sample_5991.java,2,9,temp,sample_6195.java,2,10
//,3
public class xxx {
public synchronized void unregister() {
try {
ShutdownHookManager.get().removeShutdownHook(this);
} catch (IllegalStateException e) {


log.info("failed to unregister shutdown hook");
}
}

};