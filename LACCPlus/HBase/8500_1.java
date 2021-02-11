//,temp,HMaster.java,2461,2489,temp,HMaster.java,2427,2459
//,3
public class xxx {
  @Override
  public long truncateTable(
      final TableName tableName,
      final boolean preserveSplits,
      final long nonceGroup,
      final long nonce) throws IOException {
    checkInitialized();

    return MasterProcedureUtil.submitProcedure(
        new MasterProcedureUtil.NonceProcedureRunnable(this, nonceGroup, nonce) {
      @Override
      protected void run() throws IOException {
        getMaster().getMasterCoprocessorHost().preTruncateTable(tableName);

        LOG.info(getClientIdAuditPrefix() + " truncate " + tableName);
        ProcedurePrepareLatch latch = ProcedurePrepareLatch.createLatch(2, 0);
        submitProcedure(new TruncateTableProcedure(procedureExecutor.getEnvironment(),
            tableName, preserveSplits, latch));
        latch.await();

        getMaster().getMasterCoprocessorHost().postTruncateTable(tableName);
      }

      @Override
      protected String getDescription() {
        return "TruncateTableProcedure";
      }
    });
  }

};