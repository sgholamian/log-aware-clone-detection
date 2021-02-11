//,temp,NexentaStorAppliance.java,257,266,temp,NexentaStorAppliance.java,141,150
//,2
public class xxx {
    void createIscsiTarget(String targetName) {
        try {
            client.execute(NmsResponse.class, "iscsitarget", "create_target", new CreateIscsiTargetRequestParams(targetName));
        } catch (CloudRuntimeException ex) {
            if (!ex.getMessage().contains("already configured")) {
                throw ex;
            }
            logger.debug("Ignored target creation error: " + ex);
        }
    }

};