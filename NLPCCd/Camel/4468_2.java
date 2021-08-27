//,temp,sample_6817.java,2,17,temp,sample_6706.java,2,17
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
} else {
if (log.isDebugEnabled()) {


log.info("connected to hdfs file system");
}
}
}

};