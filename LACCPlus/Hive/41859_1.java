//,temp,TestTxnCommands2.java,2885,2895,temp,TxnCommandsBaseForTests.java,242,252
//,3
public class xxx {
  private List<String> runStatementOnDriver(String stmt) throws Exception {
    LOG.info("+runStatementOnDriver(" + stmt + ")");
    try {
      d.run(stmt);
    } catch (CommandProcessorException e) {
      throw new RuntimeException(stmt + " failed: " + e);
    }
    List<String> rs = new ArrayList<>();
    d.getResults(rs);
    return rs;
  }

};