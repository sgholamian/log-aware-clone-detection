//,temp,NexentaStorAppliance.java,257,266,temp,NexentaStorAppliance.java,141,150
//,2
public class xxx {
    void createLu(String volumeName) {
        try {
            client.execute(NmsResponse.class, "scsidisk", "create_lu", volumeName, new LuParams());
        } catch (CloudRuntimeException ex) {
            if (!ex.getMessage().contains("in use")) {
                throw ex;
            }
            logger.info("Ignored LU creation error: " + ex);
        }
    }

};