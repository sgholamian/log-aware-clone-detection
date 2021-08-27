//,temp,Hive.java,5607,5614,temp,Hive.java,5531,5539
//,3
public class xxx {
  public void abortTransactions(List<Long> txnids) throws HiveException {
    try {
      getMSC().abortTxns(txnids);
    } catch (Exception e) {
      LOG.error("Failed abortTransactions", e);
      throw new HiveException(e);
    }
  }

};