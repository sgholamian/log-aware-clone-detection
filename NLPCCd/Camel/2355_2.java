//,temp,sample_6705.java,2,14,temp,sample_6816.java,2,14
//,2
public class xxx {
private HdfsInfo setupHdfs(boolean onStartup) throws Exception {
if (onStartup) {
} else {
if (log.isDebugEnabled()) {
}
}
HdfsInfo answer = HdfsInfoFactory.newHdfsInfo(this.hdfsPath.toString());
if (onStartup) {


log.info("connected to hdfs file system");
}
}

};