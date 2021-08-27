//,temp,FtpOperations.java,972,980,temp,FtpOperations.java,962,970
//,3
public class xxx {
    @Override
    public boolean sendNoop() throws GenericFileOperationFailedException {
        log.trace("sendNoOp");
        try {
            return client.sendNoOp();
        } catch (IOException e) {
            throw new GenericFileOperationFailedException(client.getReplyCode(), client.getReplyString(), e.getMessage(), e);
        }
    }

};