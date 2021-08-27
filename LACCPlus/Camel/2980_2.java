//,temp,AbstractConnectionMultiplexor.java,56,65,temp,AbstractConnectionMultiplexor.java,42,54
//,3
public class xxx {
    public synchronized Handle register() throws Exception {
        final HandleImplementation handle = new HandleImplementation();

        final boolean needStart = this.handles.isEmpty();
        this.handles.add(handle);

        if (needStart) {
            LOG.info("Calling performStart()");
            performStart();
        }

        return handle;
    }

};