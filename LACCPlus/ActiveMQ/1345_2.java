//,temp,TransportLoggerView.java,137,146,temp,TransportLoggerView.java,121,128
//,3
public class xxx {
    private void register() {
        try {
        	AnnotatedMBean.registerMBean(this.managementContext, this, this.name);
        } catch (Exception e) {
            log.error("Could not register MBean for TransportLoggerView " + id + "with name " + this.name.toString() + ", reason: " + e, e);
        }

    }

};