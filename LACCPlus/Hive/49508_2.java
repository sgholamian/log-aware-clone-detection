//,temp,ShowCompactionsOperation.java,43,66,temp,ShowTransactionsOperation.java,43,64
//,3
public class xxx {
  @Override
  public int execute() throws HiveException {
    SessionState sessionState = SessionState.get();
    // Call the metastore to get the currently queued and running compactions.
    GetOpenTxnsInfoResponse rsp = context.getDb().showTransactions();

    // Write the results into the file
    try (DataOutputStream os = ShowUtils.getOutputStream(new Path(desc.getResFile()), context)) {
      if (!sessionState.isHiveServerQuery()) {
        writeHeader(os);
      }

      for (TxnInfo txn : rsp.getOpen_txns()) {
        writeRow(os, txn);
      }
    } catch (IOException e) {
      LOG.warn("show transactions: ", e);
      return 1;
    }

    return 0;
  }

};