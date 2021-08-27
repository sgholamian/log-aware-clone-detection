//,temp,sample_1307.java,2,17,temp,sample_7209.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (config.getSplitStrategies().size() > 0) {
actualPath = newFileName();
}
if (onStartup) {
} else {
if (log.isDebugEnabled()) {
}
}
HdfsOutputStream answer = HdfsOutputStream.createOutputStream(actualPath.toString(), config);
if (onStartup) {


log.info("connected to hdfs file system");
}
}

};