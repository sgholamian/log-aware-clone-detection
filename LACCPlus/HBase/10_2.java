//,temp,EnableTableProcedure.java,370,378,temp,EnableTableProcedure.java,355,363
//,3
public class xxx {
  protected static void setTableStateToEnabling(
      final MasterProcedureEnv env,
      final TableName tableName) throws IOException {
    // Set table disabling flag up in zk.
    LOG.info("Attempting to enable the table " + tableName);
    env.getMasterServices().getTableStateManager().setTableState(
      tableName,
      TableState.State.ENABLING);
  }

};