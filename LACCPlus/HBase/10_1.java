//,temp,EnableTableProcedure.java,370,378,temp,EnableTableProcedure.java,355,363
//,3
public class xxx {
  protected static void setTableStateToEnabled(
      final MasterProcedureEnv env,
      final TableName tableName) throws IOException {
    // Flip the table to Enabled
    env.getMasterServices().getTableStateManager().setTableState(
      tableName,
      TableState.State.ENABLED);
    LOG.info("Table '" + tableName + "' was successfully enabled.");
  }

};