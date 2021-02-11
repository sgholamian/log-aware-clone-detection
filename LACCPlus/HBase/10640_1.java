//,temp,HMaster.java,2067,2084,temp,HMaster.java,1915,1926
//,3
public class xxx {
        @Override
        protected void run() throws IOException {
          getMaster().getMasterCoprocessorHost().preCreateTable(desc, newRegions);

          LOG.info(getClientIdAuditPrefix() + " create " + desc);

          // TODO: We can handle/merge duplicate requests, and differentiate the case of
          // TableExistsException by saying if the schema is the same or not.
          //
          // We need to wait for the procedure to potentially fail due to "prepare" sanity
          // checks. This will block only the beginning of the procedure. See HBASE-19953.
          ProcedurePrepareLatch latch = ProcedurePrepareLatch.createBlockingLatch();
          submitProcedure(
            new CreateTableProcedure(procedureExecutor.getEnvironment(), desc, newRegions, latch));
          latch.await();

          getMaster().getMasterCoprocessorHost().postCreateTable(desc, newRegions);
        }

};