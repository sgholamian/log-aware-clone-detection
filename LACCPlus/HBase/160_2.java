//,temp,HMaster.java,3332,3347,temp,HMaster.java,2471,2482
//,3
public class xxx {
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

};