//,temp,FtpOperations.java,972,980,temp,FtpOperations.java,962,970
//,3
public class xxx {
    @Override
    public boolean sendSiteCommand(String command) throws GenericFileOperationFailedException {
        log.trace("sendSiteCommand({})", command);
        try {
            return client.sendSiteCommand(command);
        } catch (IOException e) {
            throw new GenericFileOperationFailedException(client.getReplyCode(), client.getReplyString(), e.getMessage(), e);
        }
    }

};