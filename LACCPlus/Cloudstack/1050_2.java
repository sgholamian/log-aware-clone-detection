//,temp,Ovm3StorageProcessor.java,387,399,temp,Ovm3StorageProcessor.java,139,155
//,3
public class xxx {
    public Answer execute(DeleteCommand cmd) {
        DataTO data = cmd.getData();
        String msg;
        LOGGER.debug("Deleting object: " + data.getObjectType());
        if (data.getObjectType() == DataObjectType.VOLUME) {
            return deleteVolume(cmd);
        } else if (data.getObjectType() == DataObjectType.SNAPSHOT) {
            return deleteSnapshot(cmd);
        } else if (data.getObjectType() == DataObjectType.TEMPLATE) {
            msg = "Template deletion is not implemented yet.";
            LOGGER.info(msg);
        } else {
            msg = data.getObjectType() + " deletion is not implemented yet.";
            LOGGER.info(msg);
        }
        return new Answer(cmd, false, msg);
    }

};