//,temp,sample_1100.java,2,16,temp,sample_5180.java,2,16
//,3
public class xxx {
public static boolean moveFile(final HiveConf conf, Path srcf, final Path destf, boolean replace, boolean isSrcLocal) throws HiveException {
final FileSystem srcFs, destFs;
try {
destFs = destf.getFileSystem(conf);
} catch (IOException e) {
throw new HiveException(e.getMessage(), e);
}
try {
srcFs = srcf.getFileSystem(conf);
} catch (IOException e) {


log.info("failed to get src fs");
}
}

};