//,temp,sample_2423.java,2,13,temp,sample_1100.java,2,13
//,3
public class xxx {
public static FileStatus [] listStatus(final FileSystem fs, final Path dir, final PathFilter filter) throws IOException {
FileStatus [] status = null;
try {
status = filter == null ? fs.listStatus(dir) : fs.listStatus(dir, filter);
} catch (FileNotFoundException fnfe) {
if (LOG.isTraceEnabled()) {


log.info("doesn t exist");
}
}
}

};