//,temp,sample_7207.java,2,15,temp,sample_1305.java,2,15
//,2
public class xxx {
private synchronized HdfsOutputStream setupHdfs(boolean onStartup) throws Exception {
if (ostream != null) {
return ostream;
}
StringBuilder actualPath = new StringBuilder(hdfsPath);
if (config.getSplitStrategies().size() > 0) {
actualPath = newFileName();
}
if (onStartup) {


log.info("connecting to hdfs file system may take a while if connection is not available");
}
}

};