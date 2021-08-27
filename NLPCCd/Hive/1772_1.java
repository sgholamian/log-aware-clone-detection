//,temp,sample_1100.java,2,16,temp,sample_5180.java,2,16
//,3
public class xxx {
private void moveFileInDfs (Path sourcePath, Path targetPath, HiveConf conf) throws HiveException, IOException {
final FileSystem srcFs, tgtFs;
try {
tgtFs = targetPath.getFileSystem(conf);
} catch (IOException e) {
throw new HiveException(e.getMessage(), e);
}
try {
srcFs = sourcePath.getFileSystem(conf);
} catch (IOException e) {


log.info("failed to get src fs");
}
}

};