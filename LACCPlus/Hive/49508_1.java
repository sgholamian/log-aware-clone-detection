//,temp,ShowCompactionsOperation.java,43,66,temp,ShowTransactionsOperation.java,43,64
//,3
public class xxx {
  @Override
  public int execute() throws HiveException {
    SessionState sessionState = SessionState.get();
    // Call the metastore to get the status of all known compactions (completed get purged eventually)
    ShowCompactResponse rsp = context.getDb().showCompactions();

    // Write the results into the file
    try (DataOutputStream os = ShowUtils.getOutputStream(new Path(desc.getResFile()), context)) {
      // Write a header for cliDriver
      if (!sessionState.isHiveServerQuery()) {
        writeHeader(os);
      }

      if (rsp.getCompacts() != null) {
        for (ShowCompactResponseElement e : rsp.getCompacts()) {
          writeRow(os, e);
        }
      }
    } catch (IOException e) {
      LOG.warn("show compactions: ", e);
      return 1;
    }
    return 0;
  }

};