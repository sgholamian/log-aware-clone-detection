//,temp,FtpOperations.java,790,821,temp,SftpOperations.java,1050,1090
//,3
public class xxx {
    @Override
    public synchronized boolean existsFile(String name) throws GenericFileOperationFailedException {
        LOG.trace("existsFile({})", name);
        if (endpoint.isFastExistsCheck()) {
            return fastExistsFile(name);
        }
        // check whether a file already exists
        String directory = FileUtil.onlyPath(name);
        if (directory == null) {
            // assume current dir if no path could be extracted
            directory = ".";
        }
        String onlyName = FileUtil.stripPath(name);

        try {
            @SuppressWarnings("rawtypes")
            List files = channel.ls(directory);
            // can return either null or an empty list depending on FTP servers
            if (files == null) {
                return false;
            }
            for (Object file : files) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) file;
                String existing = entry.getFilename();
                LOG.trace("Existing file: {}, target file: {}", existing, name);
                existing = FileUtil.stripPath(existing);
                if (existing != null && existing.equals(onlyName)) {
                    return true;
                }
            }
            return false;
        } catch (SftpException e) {
            // or an exception can be thrown with id 2 which means file does not
            // exists
            if (ChannelSftp.SSH_FX_NO_SUCH_FILE == e.id) {
                return false;
            }
            // otherwise its a more serious error so rethrow
            throw new GenericFileOperationFailedException(e.getMessage(), e);
        }
    }

};