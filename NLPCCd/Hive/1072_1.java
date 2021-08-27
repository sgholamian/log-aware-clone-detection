//,temp,sample_4863.java,2,16,temp,sample_4864.java,2,16
//,3
public class xxx {
public void dummy_method(){
int shufflePort = daemonConf .getInt(ShuffleHandler.SHUFFLE_PORT_CONFIG_KEY, ShuffleHandler.DEFAULT_SHUFFLE_PORT);
int webPort = HiveConf.getIntVar(daemonConf, ConfVars.LLAP_DAEMON_WEB_PORT);
LlapDaemonInfo.initialize(appName, daemonConf);
int numExecutors = LlapDaemonInfo.INSTANCE.getNumExecutors();
long executorMemoryBytes = LlapDaemonInfo.INSTANCE.getExecutorMemory();
long ioMemoryBytes = LlapDaemonInfo.INSTANCE.getCacheSize();
boolean isDirectCache = LlapDaemonInfo.INSTANCE.isDirectCache();
boolean isLlapIo = LlapDaemonInfo.INSTANCE.isLlapIo();
LlapDaemon.initializeLogging(daemonConf);
llapDaemon = new LlapDaemon(daemonConf, numExecutors, executorMemoryBytes, isLlapIo, isDirectCache, ioMemoryBytes, localDirs, rpcPort, mngPort, shufflePort, webPort, appName);


log.info("adding shutdown hook for llapdaemon");
}

};