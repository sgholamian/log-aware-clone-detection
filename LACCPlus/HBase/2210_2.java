//,temp,DisableTableProcedure.java,302,308,temp,DisableTableProcedure.java,290,296
//,2
public class xxx {
  private static void setTableStateToDisabling(final MasterProcedureEnv env,
      final TableName tableName) throws IOException {
    // Set table disabling flag up in zk.
    env.getMasterServices().getTableStateManager().setTableState(tableName,
      TableState.State.DISABLING);
    LOG.info("Set {} to state={}", tableName, TableState.State.DISABLING);
  }

};