//,temp,NexentaStorAppliance.java,324,334,temp,NexentaStorAppliance.java,141,150
//,3
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