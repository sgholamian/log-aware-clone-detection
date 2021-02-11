//,temp,HMaster.java,3332,3347,temp,HMaster.java,2471,2482
//,3
public class xxx {
      @Override
      protected void run() throws IOException {
        getMaster().getMasterCoprocessorHost().preDeleteNamespace(name);
        LOG.info(getClientIdAuditPrefix() + " delete " + name);
        // Execute the operation synchronously - wait for the operation to complete before
        // continuing.
        //
        // We need to wait for the procedure to potentially fail due to "prepare" sanity
        // checks. This will block only the beginning of the procedure. See HBASE-19953.
        ProcedurePrepareLatch latch = ProcedurePrepareLatch.createBlockingLatch();
        setProcId(submitProcedure(
              new DeleteNamespaceProcedure(procedureExecutor.getEnvironment(), name, latch)));
        latch.await();
        // Will not be invoked in the face of Exception thrown by the Procedure's execution
        getMaster().getMasterCoprocessorHost().postDeleteNamespace(name);
      }

};