//,temp,sample_6184.java,2,10,temp,sample_6185.java,2,10
//,2
public class xxx {
private void initialize(Configuration conf) throws IOException {
shouldRun = true;
checkpointConf = new CheckpointConf(conf);
String fullInfoAddr = conf.get(DFS_NAMENODE_BACKUP_HTTP_ADDRESS_KEY, DFS_NAMENODE_BACKUP_HTTP_ADDRESS_DEFAULT);
infoBindAddress = fullInfoAddr.substring(0, fullInfoAddr.indexOf(":"));


log.info("checkpoint period secs min");
}

};