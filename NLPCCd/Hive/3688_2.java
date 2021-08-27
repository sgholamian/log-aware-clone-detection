//,temp,sample_1946.java,2,18,temp,sample_1947.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (hiveConf != null && hiveConf.getBoolVar(ConfVars.HIVE_SERVER2_SUPPORT_DYNAMIC_SERVICE_DISCOVERY)) {
try {
removeServerInstanceFromZooKeeper();
} catch (Exception e) {
}
}
if (hiveConf != null && hiveConf.getBoolVar(ConfVars.HIVE_SERVER2_TEZ_INITIALIZE_DEFAULT_SESSIONS) && tezSessionPoolManager != null) {
try {
tezSessionPoolManager.stop();
} catch (Exception e) {


log.info("tez session pool manager stop had an error during stop of shutting down anyway");
}
}
}

};