//,temp,sample_6704.java,2,11,temp,sample_6815.java,2,11
//,2
public class xxx {
private HdfsInfo setupHdfs(boolean onStartup) throws Exception {
if (onStartup) {
} else {
if (log.isDebugEnabled()) {


log.info("connecting to hdfs file system may take a while if connection is not available");
}
}
}

};