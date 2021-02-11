//,temp,sample_3555.java,2,13,temp,sample_3556.java,2,15
//,3
public class xxx {
protected LockState acquireLock(final MasterProcedureEnv env) {
boolean ret = lock.acquireLock(env);
locked.set(ret);
hasLock = ret;
if (ret) {
if (LOG.isDebugEnabled()) {
}
lastHeartBeat.set(System.currentTimeMillis());
return LockState.LOCK_ACQUIRED;
}


log.info("failed acquire lock yielding");
}

};