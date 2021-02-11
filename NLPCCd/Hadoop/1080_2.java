//,temp,sample_891.java,2,10,temp,sample_9604.java,2,11
//,3
public class xxx {
public FSDataOutputStream create(Path f, FsPermission permission, boolean overwrite, int bufferSize, short replication, long blockSize, Progressable progress) throws IOException {
if (exists(f) && !overwrite) {
throw new FileAlreadyExistsException("File already exists: " + f);
}
if(LOG.isDebugEnabled()) {


log.info("creating new file in");
}
}

};