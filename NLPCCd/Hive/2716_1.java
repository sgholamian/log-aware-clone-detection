//,temp,sample_5266.java,2,16,temp,sample_5267.java,2,17
//,3
public class xxx {
private Path createRootHDFSDir(HiveConf conf) throws IOException {
Path rootHDFSDirPath = new Path(HiveConf.getVar(conf, HiveConf.ConfVars.SCRATCHDIR));
FsPermission writableHDFSDirPermission = new FsPermission((short)00733);
FileSystem fs = rootHDFSDirPath.getFileSystem(conf);
if (!fs.exists(rootHDFSDirPath)) {
Utilities.createDirsWithPermission(conf, rootHDFSDirPath, writableHDFSDirPermission, true);
}
FsPermission currentHDFSDirPermission = fs.getFileStatus(rootHDFSDirPath).getPermission();
if (rootHDFSDirPath != null && rootHDFSDirPath.toUri() != null) {
String schema = rootHDFSDirPath.toUri().getScheme();


log.info("hdfs root scratch dir with schema permission");
}
}

};