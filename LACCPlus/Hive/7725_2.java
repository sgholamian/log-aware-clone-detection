//,temp,DbTxnManager.java,544,558,temp,DbTxnManager.java,517,543
//,3
public class xxx {
  @Override
  public void commitTxn() throws LockException {
    if (!isTxnOpen()) {
      throw new RuntimeException("Attempt to commit before opening a transaction");
    }
    try {
      // do all new clear in clearLocksAndHB method to make sure that same code is there for replCommitTxn flow.
      clearLocksAndHB();
      LOG.debug("Committing txn " + JavaUtils.txnIdToString(txnId));
      CommitTxnRequest commitTxnRequest = new CommitTxnRequest(txnId);
      commitTxnRequest.setExclWriteEnabled(conf.getBoolVar(HiveConf.ConfVars.TXN_WRITE_X_LOCK));
      getMS().commitTxn(commitTxnRequest);
    } catch (NoSuchTxnException e) {
      LOG.error("Metastore could not find " + JavaUtils.txnIdToString(txnId));
      throw new LockException(e, ErrorMsg.TXN_NO_SUCH_TRANSACTION, JavaUtils.txnIdToString(txnId));
    } catch (TxnAbortedException e) {
      LockException le = new LockException(e, ErrorMsg.TXN_ABORTED, JavaUtils.txnIdToString(txnId), e.getMessage());
      LOG.error(le.getMessage());
      throw le;
    } catch (TException e) {
      throw new LockException(ErrorMsg.METASTORE_COMMUNICATION_FAILED.getMsg(),
          e);
    } finally {
      // do all new reset in resetTxnInfo method to make sure that same code is there for replCommitTxn flow.
      resetTxnInfo();
    }
  }

};