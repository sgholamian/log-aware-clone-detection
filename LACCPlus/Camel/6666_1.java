//,temp,FtpOperations.java,790,821,temp,SftpOperations.java,1050,1090
//,3
public class xxx {
    @Override
    public boolean existsFile(String name) throws GenericFileOperationFailedException {
        log.trace("existsFile({})", name);
        if (endpoint.isFastExistsCheck()) {
            return fastExistsFile(name);
        }
        // check whether a file already exists
        String directory = FileUtil.onlyPath(name);
        String onlyName = FileUtil.stripPath(name);
        try {
            String[] names;
            if (directory != null) {
                names = client.listNames(directory);
            } else {
                names = client.listNames();
            }
            // can return either null or an empty list depending on FTP servers
            if (names == null) {
                return false;
            }
            for (String existing : names) {
                log.trace("Existing file: {}, target file: {}", existing, name);
                existing = FileUtil.stripPath(existing);
                if (existing != null && existing.equals(onlyName)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new GenericFileOperationFailedException(client.getReplyCode(), client.getReplyString(), e.getMessage(), e);
        }
    }

};