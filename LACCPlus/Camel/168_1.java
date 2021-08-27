//,temp,SftpConsumer.java,213,225,temp,FileConsumer.java,180,191
//,2
public class xxx {
    @Override
    protected boolean isMatched(GenericFile<SftpRemoteFile> file, String doneFileName, List<SftpRemoteFile> files) {
        String onlyName = FileUtil.stripPath(doneFileName);

        for (SftpRemoteFile f : files) {
            if (f.getFilename().equals(onlyName)) {
                return true;
            }
        }

        LOG.trace("Done file: {} does not exist", doneFileName);
        return false;
    }

};