//,temp,sample_4284.java,2,15,temp,sample_4286.java,2,15
//,2
public class xxx {
public void rollbackTxn() throws LockException {
if (!isTxnOpen()) {
throw new RuntimeException("Attempt to rollback before opening a transaction");
}
try {
lockMgr.clearLocalLockRecords();
stopHeartbeat();
getMS().rollbackTxn(txnId);
} catch (NoSuchTxnException e) {


log.info("metastore could not find");
}
}

};