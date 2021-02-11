//,temp,sample_5592.java,2,11,temp,sample_380.java,2,10
//,3
public class xxx {
public static void visitRegionStoreFiles(final FileSystem fs, final Path regionDir, final StoreFileVisitor visitor) throws IOException {
List<FileStatus> families = FSUtils.listStatusWithStatusFilter(fs, regionDir, new FSUtils.FamilyDirFilter(fs));
if (families == null) {
if (LOG.isTraceEnabled()) {


log.info("no families under region directory");
}
}
}

};