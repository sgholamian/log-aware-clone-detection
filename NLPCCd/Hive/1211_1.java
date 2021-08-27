//,temp,sample_1099.java,2,11,temp,sample_5179.java,2,11
//,3
public class xxx {
private void moveFileInDfs (Path sourcePath, Path targetPath, HiveConf conf) throws HiveException, IOException {
final FileSystem srcFs, tgtFs;
try {
tgtFs = targetPath.getFileSystem(conf);
} catch (IOException e) {


log.info("failed to get dest fs");
}
}

};