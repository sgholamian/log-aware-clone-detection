//,temp,SftpOperations.java,501,515,temp,SftpOperations.java,488,499
//,3
public class xxx {
    @Override
    public synchronized boolean deleteFile(String name) throws GenericFileOperationFailedException {
        LOG.debug("Deleting file: {}", name);
        try {
            reconnectIfNecessary(null);
            channel.rm(name);
            return true;
        } catch (SftpException e) {
            LOG.debug("Cannot delete file: {}", name, e);
            throw new GenericFileOperationFailedException("Cannot delete file: " + name, e);
        }
    }

};