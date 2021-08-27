//,temp,HdfsComponent.java,61,70,temp,SftpOperations.java,602,612
//,3
public class xxx {
    @Override
    public synchronized String getCurrentDirectory() throws GenericFileOperationFailedException {
        LOG.trace("getCurrentDirectory()");
        try {
            String answer = channel.pwd();
            LOG.trace("Current dir: {}", answer);
            return answer;
        } catch (SftpException e) {
            throw new GenericFileOperationFailedException("Cannot get current directory", e);
        }
    }

};