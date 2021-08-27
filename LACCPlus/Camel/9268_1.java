//,temp,FtpOperations.java,938,960,temp,FtpOperations.java,920,936
//,3
public class xxx {
    @Override
    public List<FTPFile> listFiles(String path) throws GenericFileOperationFailedException {
        log.trace("listFiles({})", path);
        clientActivityListener.onScanningForFiles(endpoint.remoteServerInformation(), path);

        // use current directory if path not given
        if (org.apache.camel.util.ObjectHelper.isEmpty(path)) {
            path = ".";
        }

        try {
            final List<FTPFile> list = new ArrayList<>();
            FTPFile[] files = client.listFiles(path);
            // can return either null or an empty list depending on FTP servers
            if (files != null) {
                list.addAll(Arrays.asList(files));
            }
            return list;
        } catch (IOException e) {
            clientActivityListener.onGeneralError(endpoint.getConfiguration().remoteServerInformation(), e.getMessage());
            throw new GenericFileOperationFailedException(client.getReplyCode(), client.getReplyString(), e.getMessage(), e);
        }
    }

};