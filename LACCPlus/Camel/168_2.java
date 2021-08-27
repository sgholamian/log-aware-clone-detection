//,temp,SftpConsumer.java,213,225,temp,FileConsumer.java,180,191
//,2
public class xxx {
    @Override
    protected boolean isMatched(GenericFile<File> file, String doneFileName, List<File> files) {
        String onlyName = FileUtil.stripPath(doneFileName);
        // the done file name must be among the files
        for (File f : files) {
            if (f.getName().equals(onlyName)) {
                return true;
            }
        }
        LOG.trace("Done file: {} does not exist", doneFileName);
        return false;
    }

};