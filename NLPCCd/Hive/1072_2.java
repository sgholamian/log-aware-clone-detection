//,temp,sample_4863.java,2,16,temp,sample_4864.java,2,16
//,3
public class xxx {
public void dummy_method(){
int numExecutors = LlapDaemonInfo.INSTANCE.getNumExecutors();
long executorMemoryBytes = LlapDaemonInfo.INSTANCE.getExecutorMemory();
long ioMemoryBytes = LlapDaemonInfo.INSTANCE.getCacheSize();
boolean isDirectCache = LlapDaemonInfo.INSTANCE.isDirectCache();
boolean isLlapIo = LlapDaemonInfo.INSTANCE.isLlapIo();
LlapDaemon.initializeLogging(daemonConf);
llapDaemon = new LlapDaemon(daemonConf, numExecutors, executorMemoryBytes, isLlapIo, isDirectCache, ioMemoryBytes, localDirs, rpcPort, mngPort, shufflePort, webPort, appName);
ShutdownHookManager.addShutdownHook(new CompositeServiceShutdownHook(llapDaemon), 1);
llapDaemon.init(daemonConf);
llapDaemon.start();


log.info("started llapdaemon with pid");
}

};