//,temp,TransportLoggerView.java,137,146,temp,TransportLoggerView.java,121,128
//,3
public class xxx {
    public void unregister() {
        
        TransportLoggerView.transportLoggerViews.remove(this);
        
        try {
            this.managementContext.unregisterMBean(this.name);
        } catch (Exception e) {
            log.error("Could not unregister MBean for TransportLoggerView " + id + "with name " + this.name.toString() + ", reason: " + e, e);
        }
    }

};