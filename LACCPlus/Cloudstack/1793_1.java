//,temp,Ovm3StorageProcessor.java,387,399,temp,StorageSubsystemCommandHandlerBase.java,115,128
//,3
public class xxx {
    public Answer execute(CreateObjectCommand cmd) {
        LOGGER.debug("execute: "+ cmd.getClass());
        DataTO data = cmd.getData();
        if (data.getObjectType() == DataObjectType.VOLUME) {
            return createVolume(cmd);
        } else if (data.getObjectType() == DataObjectType.SNAPSHOT) {
            return createSnapshot(cmd);
        } else if (data.getObjectType() == DataObjectType.TEMPLATE) {
            LOGGER.debug("Template object creation not supported.");
        }
        return new CreateObjectAnswer(data.getObjectType()
                + " object creation not supported");
    }

};