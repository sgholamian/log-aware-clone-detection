//,temp,sample_1099.java,2,11,temp,sample_5179.java,2,11
//,3
public class xxx {
public static boolean moveFile(final HiveConf conf, Path srcf, final Path destf, boolean replace, boolean isSrcLocal) throws HiveException {
final FileSystem srcFs, destFs;
try {
destFs = destf.getFileSystem(conf);
} catch (IOException e) {


log.info("failed to get dest fs");
}
}

};