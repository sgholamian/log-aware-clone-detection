//,temp,CamelKieServerExtension.java,211,220,temp,CamelKieServerExtension.java,114,125
//,3
public class xxx {
    @Override
    public void destroy(KieServerImpl kieServer, KieServerRegistry registry) {
        ServiceRegistry.get().remove("GlobalCamelService");

        if (this.managedCamel && this.camelContext != null) {
            try {
                this.camelContext.stop();
            } catch (Exception e) {
                LOGGER.error("Failed at stopping KIE Server extension {}", EXTENSION_NAME);
            }
        }
    }

};