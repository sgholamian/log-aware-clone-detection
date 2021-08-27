//,temp,DriverTxnHandler.java,336,349,temp,Compiler.java,326,336
//,3
public class xxx {
  ValidTxnWriteIdList recordValidWriteIds() throws LockException {
    String txnString = driverContext.getConf().get(ValidTxnList.VALID_TXNS_KEY);
    if (Strings.isNullOrEmpty(txnString)) {
      throw new IllegalStateException("calling recordValidWritsIdss() without initializing ValidTxnList " +
          JavaUtils.txnIdToString(driverContext.getTxnManager().getCurrentTxnId()));
    }

    ValidTxnWriteIdList txnWriteIds = getTxnWriteIds(txnString);
    setValidWriteIds(txnWriteIds);

    LOG.debug("Encoding valid txn write ids info {} txnid: {}", txnWriteIds.toString(),
        driverContext.getTxnManager().getCurrentTxnId());
    return txnWriteIds;
  }

};