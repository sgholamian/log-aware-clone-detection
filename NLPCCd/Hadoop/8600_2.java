//,temp,sample_9605.java,2,13,temp,sample_9615.java,2,10
//,3
public class xxx {
public FSDataInputStream open(Path f, int bufferSize) throws IOException {
FileStatus fs = getFileStatus(f);
if (fs.isDirectory()) {
throw new FileNotFoundException("'" + f + "' is a directory");
}


log.info("opening for reading");
}

};