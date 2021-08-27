//,temp,sample_3074.java,2,11,temp,sample_3075.java,2,11
//,2
public class xxx {
public void setInputFormatZooKeeperInstance(JobConf conf, String instanceName, String zookeepers, boolean isSasl) throws IOException {
try {
ClientConfiguration clientConf = getClientConfiguration(zookeepers, instanceName, isSasl);
AccumuloInputFormat.setZooKeeperInstance(conf, clientConf);
} catch (IllegalStateException ise) {


log.info("ignoring exception setting zookeeper instance of at");
}
}

};