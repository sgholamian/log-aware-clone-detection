//,temp,sample_9341.java,2,14,temp,sample_9605.java,2,13
//,3
public class xxx {
public boolean delete(Path f, boolean recurse) throws IOException {
FileStatus status;
try {
status = getFileStatus(f);
} catch (FileNotFoundException e) {
if(LOG.isDebugEnabled()) {


log.info("delete called for but file does not exist so returning false");
}
}
}

};