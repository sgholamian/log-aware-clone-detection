//,temp,sample_4284.java,2,15,temp,sample_4286.java,2,15
//,2
public class xxx {
public void commitTxn() throws LockException {
if (!isTxnOpen()) {
throw new RuntimeException("Attempt to commit before opening a transaction");
}
try {
lockMgr.clearLocalLockRecords();
stopHeartbeat();
getMS().commitTxn(txnId);
} catch (NoSuchTxnException e) {


log.info("metastore could not find");
}
}

};