//,temp,sample_2423.java,2,13,temp,sample_1100.java,2,13
//,3
public class xxx {
public static List<FileStatus> listStatusWithStatusFilter(final FileSystem fs, final Path dir, final FileStatusFilter filter) throws IOException {
FileStatus [] status = null;
try {
status = fs.listStatus(dir);
} catch (FileNotFoundException fnfe) {
if (LOG.isTraceEnabled()) {


log.info("doesn t exist");
}
}
}

};