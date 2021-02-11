//,temp,CloudStackPrimaryDataStoreDriverImpl.java,127,143,temp,TemplateManagerImpl.java,712,728
//,3
public class xxx {
    public Answer createVolume(VolumeInfo volume) throws StorageUnavailableException {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Creating volume: " + volume);
        }

        CreateObjectCommand cmd = new CreateObjectCommand(volume.getTO());
        EndPoint ep = epSelector.select(volume);
        Answer answer = null;
        if (ep == null) {
            String errMsg = "No remote endpoint to send DeleteCommand, check if host or ssvm is down?";
            s_logger.error(errMsg);
            answer = new Answer(cmd, false, errMsg);
        } else {
            answer = ep.sendMessage(cmd);
        }
        return answer;
    }

};