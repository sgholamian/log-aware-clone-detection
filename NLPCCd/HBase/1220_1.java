//,temp,sample_4746.java,2,8,temp,sample_1332.java,2,8
//,3
public class xxx {
public static void archiveStoreFiles(Configuration conf, FileSystem fs, RegionInfo regionInfo, Path tableDir, byte[] family, Collection<HStoreFile> compactedFiles) throws IOException, FailedArchiveException {
if (fs == null) {


log.info("passed filesystem is null so just deleting the files without archiving for region family");
}
}

};