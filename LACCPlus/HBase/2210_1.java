//,temp,DisableTableProcedure.java,302,308,temp,DisableTableProcedure.java,290,296
//,2
public class xxx {
  protected static void setTableStateToDisabled(final MasterProcedureEnv env,
      final TableName tableName) throws IOException {
    // Flip the table to disabled
    env.getMasterServices().getTableStateManager().setTableState(tableName,
      TableState.State.DISABLED);
    LOG.info("Set {} to state={}", tableName, TableState.State.DISABLED);
  }

};