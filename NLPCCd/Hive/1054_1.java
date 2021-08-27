//,temp,sample_2516.java,2,17,temp,sample_2518.java,2,18
//,3
public class xxx {
public OperationStatus getOperationStatus(OperationHandle opHandle, boolean getProgressUpdate) throws HiveSQLException {
Operation operation = sessionManager.getOperationManager().getOperation(opHandle);
HiveConf conf = operation.getParentSession().getHiveConf();
if (operation.shouldRunAsync()) {
long maxTimeout = HiveConf.getTimeVar(conf, HiveConf.ConfVars.HIVE_SERVER2_LONG_POLLING_TIMEOUT, TimeUnit.MILLISECONDS);
final long elapsed = System.currentTimeMillis() - operation.getBeginTime();
final long timeout = Math.min(maxTimeout, (elapsed / TimeUnit.SECONDS.toMillis(10) + 1) * 500);
try {
operation.getBackgroundHandle().get(timeout, TimeUnit.MILLISECONDS);
} catch (TimeoutException e) {


log.info("long polling timed out");
}
}
}

};