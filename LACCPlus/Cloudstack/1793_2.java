//,temp,Ovm3StorageProcessor.java,387,399,temp,StorageSubsystemCommandHandlerBase.java,115,128
//,3
public class xxx {
    protected Answer execute(CreateObjectCommand cmd) {
        DataTO data = cmd.getData();
        try {
            if (data.getObjectType() == DataObjectType.VOLUME) {
                return processor.createVolume(cmd);
            } else if (data.getObjectType() == DataObjectType.SNAPSHOT) {
                return processor.createSnapshot(cmd);
            }
            return new CreateObjectAnswer("not supported type");
        } catch (Exception e) {
            s_logger.debug("Failed to create object: " + data.getObjectType() + ": " + e.toString());
            return new CreateObjectAnswer(e.toString());
        }
    }

};