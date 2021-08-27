//,temp,sample_1946.java,2,18,temp,sample_1947.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (MetricsFactory.getInstance() != null) {
try {
MetricsFactory.close();
} catch (Exception e) {
}
}
if (hiveConf != null && hiveConf.getBoolVar(ConfVars.HIVE_SERVER2_SUPPORT_DYNAMIC_SERVICE_DISCOVERY)) {
try {
removeServerInstanceFromZooKeeper();
} catch (Exception e) {


log.info("error removing znode for this instance from zookeeper");
}
}
}

};