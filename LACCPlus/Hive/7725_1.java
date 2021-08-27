//,temp,DbTxnManager.java,544,558,temp,DbTxnManager.java,517,543
//,3
public class xxx {
  @Override
  public void replRollbackTxn(String replPolicy, long srcTxnId) throws LockException {
    try {
      getMS().replRollbackTxn(srcTxnId, replPolicy);
    } catch (NoSuchTxnException e) {
      LOG.error("Metastore could not find " + JavaUtils.txnIdToString(srcTxnId));
      throw new LockException(e, ErrorMsg.TXN_NO_SUCH_TRANSACTION, JavaUtils.txnIdToString(srcTxnId));
    } catch (TxnAbortedException e) {
      LockException le = new LockException(e, ErrorMsg.TXN_ABORTED, JavaUtils.txnIdToString(srcTxnId), e.getMessage());
      LOG.error(le.getMessage());
      throw le;
    } catch (TException e) {
      throw new LockException(ErrorMsg.METASTORE_COMMUNICATION_FAILED.getMsg(), e);
    }
  }

};