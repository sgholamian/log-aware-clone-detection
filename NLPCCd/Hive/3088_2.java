//,temp,sample_3219.java,2,13,temp,sample_3437.java,2,13
//,2
public class xxx {
public void run() {
TxnStore.MutexAPI.LockHandle handle = null;
try {
handle = txnHandler.getMutexAPI().acquireLock(TxnStore.MUTEX_KEY.WriteSetCleaner.name());
long startTime = System.currentTimeMillis();
txnHandler.performWriteSetGC();
} catch(Throwable t) {


log.info("serious error in");
}
}

};