//,temp,sample_297.java,2,17,temp,sample_296.java,2,13
//,3
public class xxx {
public void dummy_method(){
if (TXN_STATUS.ROLLBACK == transactionStatus) {
debugLog("Commit transaction: rollback");
return false;
}
if (openTrasactionCalls <= 0) {
RuntimeException e = new RuntimeException("commitTransaction was called but openTransactionCalls = " + openTrasactionCalls + ". This probably indicates that there are unbalanced " + "calls to openTransaction/commitTransaction");
throw e;
}
if (!currentTransaction.isActive()) {
RuntimeException e = new RuntimeException("commitTransaction was called but openTransactionCalls = " + openTrasactionCalls + ". This probably indicates that there are unbalanced " + "calls to openTransaction/commitTransaction");


log.info("unbalanced calls to open commit transaction");
}
}

};