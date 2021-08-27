//,temp,CamelKieServerExtension.java,211,220,temp,CamelKieServerExtension.java,114,125
//,3
public class xxx {
    @Override
    public void serverStarted() {
        if (this.managedCamel && this.camelContext != null && !this.camelContext.isStarted()) {
            try {
                this.camelContext.start();
            } catch (Exception e) {
                LOGGER.error("Failed at start Camel context", e);
            }
        }
    }

};