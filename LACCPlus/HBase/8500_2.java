//,temp,HMaster.java,2461,2489,temp,HMaster.java,2427,2459
//,3
public class xxx {
  @Override
  public long deleteTable(
      final TableName tableName,
      final long nonceGroup,
      final long nonce) throws IOException {
    checkInitialized();

    return MasterProcedureUtil.submitProcedure(
        new MasterProcedureUtil.NonceProcedureRunnable(this, nonceGroup, nonce) {
      @Override
      protected void run() throws IOException {
        getMaster().getMasterCoprocessorHost().preDeleteTable(tableName);

        LOG.info(getClientIdAuditPrefix() + " delete " + tableName);

        // TODO: We can handle/merge duplicate request
        //
        // We need to wait for the procedure to potentially fail due to "prepare" sanity
        // checks. This will block only the beginning of the procedure. See HBASE-19953.
        ProcedurePrepareLatch latch = ProcedurePrepareLatch.createBlockingLatch();
        submitProcedure(new DeleteTableProcedure(procedureExecutor.getEnvironment(),
            tableName, latch));
        latch.await();

        getMaster().getMasterCoprocessorHost().postDeleteTable(tableName);
      }

      @Override
      protected String getDescription() {
        return "DeleteTableProcedure";
      }
    });
  }

};