//,temp,NexentaStorAppliance.java,216,225,temp,NexentaStorAppliance.java,185,194
//,3
public class xxx {
    void createIscsiTargetGroup(String targetGroupName) {
        try {
            client.execute(NmsResponse.class, "stmf", "create_targetgroup", targetGroupName);
        } catch (CloudRuntimeException ex) {
            if (!ex.getMessage().contains("already exists") && !ex.getMessage().contains("target must be offline")) {
                throw ex;
            }
            logger.info("Ignored target group creation error: " + ex);
        }
    }

};