//,temp,sample_1948.java,2,18,temp,sample_1358.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (hiveConf != null && hiveConf.getBoolVar(ConfVars.HIVE_SERVER2_TEZ_INITIALIZE_DEFAULT_SESSIONS) && tezSessionPoolManager != null) {
try {
tezSessionPoolManager.stop();
} catch (Exception e) {
}
}
if (wm != null) {
try {
wm.stop();
} catch (Exception e) {


log.info("workload manager stop had an error during stop of shutting down anyway");
}
}
}

};