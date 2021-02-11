//,temp,NexentaStorAppliance.java,398,409,temp,NexentaStorAppliance.java,324,334
//,3
public class xxx {
    public void deleteIscsiVolume(String volumeName) {
        try {
            NmsResponse response = client.execute(NmsResponse.class, "zvol", "destroy", volumeName, "");
        } catch (CloudRuntimeException ex) {
            if (!ex.getMessage().contains("does not exist")) {
                throw ex;
            }
            logger.debug(String.format(
                    "Volume %s does not exist, it seems it was already " +
                            "deleted.", volumeName));
        }
    }

};