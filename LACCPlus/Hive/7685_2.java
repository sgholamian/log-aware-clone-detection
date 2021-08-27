//,temp,DriverTxnHandler.java,336,349,temp,Compiler.java,326,336
//,3
public class xxx {
  private void recordValidTxns(HiveTxnManager txnMgr) throws LockException {
    String oldTxnString = driverContext.getConf().get(ValidTxnList.VALID_TXNS_KEY);
    if ((oldTxnString != null) && (oldTxnString.length() > 0)) {
      throw new IllegalStateException("calling recordValidTxn() more than once in the same " +
              JavaUtils.txnIdToString(txnMgr.getCurrentTxnId()));
    }
    ValidTxnList txnList = txnMgr.getValidTxns();
    String txnStr = txnList.toString();
    driverContext.getConf().set(ValidTxnList.VALID_TXNS_KEY, txnStr);
    LOG.debug("Encoding valid txns info " + txnStr + " txnid:" + txnMgr.getCurrentTxnId());
  }

};