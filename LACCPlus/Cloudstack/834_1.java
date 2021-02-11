//,temp,NexentaStorAppliance.java,216,225,temp,NexentaStorAppliance.java,141,150
//,3
public class xxx {
    void addTargetGroupMember(String targetGroupName, String targetName) {
        try {
            client.execute(NmsResponse.class, "stmf", "add_targetgroup_member", targetGroupName, targetName);
        } catch (CloudRuntimeException ex) {
            if (!ex.getMessage().contains("already exists") && !ex.getMessage().contains("target must be offline")) {
                throw ex;
            }
            logger.debug("Ignored target group member addition error: " + ex);
        }
    }

};