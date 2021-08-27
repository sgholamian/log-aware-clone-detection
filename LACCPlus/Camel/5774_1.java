//,temp,SftpOperations.java,501,515,temp,SftpOperations.java,488,499
//,3
public class xxx {
    @Override
    public synchronized boolean renameFile(String from, String to) throws GenericFileOperationFailedException {
        LOG.debug("Renaming file: {} to: {}", from, to);
        try {
            reconnectIfNecessary(null);
            // make use of the '/' separator because JSch expects this
            // as the file separator even on Windows
            to = FileUtil.compactPath(to, '/');
            channel.rename(from, to);
            return true;
        } catch (SftpException e) {
            LOG.debug("Cannot rename file from: " + from + " to: " + to, e);
            throw new GenericFileOperationFailedException("Cannot rename file from: " + from + " to: " + to, e);
        }
    }

};