//,temp,AbstractConnectionMultiplexor.java,56,65,temp,AbstractConnectionMultiplexor.java,42,54
//,3
public class xxx {
    private synchronized void unregister(final HandleImplementation handle) throws Exception {
        if (!this.handles.remove(handle)) {
            return;
        }

        if (this.handles.isEmpty()) {
            LOG.info("Calling performStop()");
            performStop();
        }
    }

};