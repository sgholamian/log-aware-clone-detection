//,temp,sample_1306.java,2,18,temp,sample_7208.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (ostream != null) {
return ostream;
}
StringBuilder actualPath = new StringBuilder(hdfsPath);
if (config.getSplitStrategies().size() > 0) {
actualPath = newFileName();
}
if (onStartup) {
} else {
if (log.isDebugEnabled()) {


log.info("connecting to hdfs file system may take a while if connection is not available");
}
}
}

};