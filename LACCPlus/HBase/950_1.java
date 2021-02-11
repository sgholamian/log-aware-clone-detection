//,temp,HMaster.java,2653,2687,temp,HMaster.java,2051,2091
//,3
public class xxx {
  private long modifyTable(final TableName tableName,
      final TableDescriptorGetter newDescriptorGetter, final long nonceGroup, final long nonce,
      final boolean shouldCheckDescriptor) throws IOException {
    return MasterProcedureUtil
        .submitProcedure(new MasterProcedureUtil.NonceProcedureRunnable(this, nonceGroup, nonce) {
          @Override
          protected void run() throws IOException {
            TableDescriptor oldDescriptor = getMaster().getTableDescriptors().get(tableName);
            TableDescriptor newDescriptor = getMaster().getMasterCoprocessorHost()
                .preModifyTable(tableName, oldDescriptor, newDescriptorGetter.get());
            sanityCheckTableDescriptor(newDescriptor);
            LOG.info("{} modify table {} from {} to {}", getClientIdAuditPrefix(), tableName,
                oldDescriptor, newDescriptor);

            // Execute the operation synchronously - wait for the operation completes before
            // continuing.
            //
            // We need to wait for the procedure to potentially fail due to "prepare" sanity
            // checks. This will block only the beginning of the procedure. See HBASE-19953.
            ProcedurePrepareLatch latch = ProcedurePrepareLatch.createBlockingLatch();
            submitProcedure(new ModifyTableProcedure(procedureExecutor.getEnvironment(),
                newDescriptor, latch, oldDescriptor, shouldCheckDescriptor));
            latch.await();

            getMaster().getMasterCoprocessorHost().postModifyTable(tableName, oldDescriptor,
              newDescriptor);
          }

          @Override
          protected String getDescription() {
            return "ModifyTableProcedure";
          }
        });

  }

};