//,temp,HMaster.java,2653,2687,temp,HMaster.java,2051,2091
//,3
public class xxx {
  @Override
  public long createTable(final TableDescriptor tableDescriptor, final byte[][] splitKeys,
      final long nonceGroup, final long nonce) throws IOException {
    checkInitialized();
    TableDescriptor desc = getMasterCoprocessorHost().preCreateTableRegionsInfos(tableDescriptor);
    if (desc == null) {
      throw new IOException("Creation for " + tableDescriptor + " is canceled by CP");
    }
    String namespace = desc.getTableName().getNamespaceAsString();
    this.clusterSchemaService.getNamespace(namespace);

    RegionInfo[] newRegions = ModifyRegionUtils.createRegionInfos(desc, splitKeys);
    sanityCheckTableDescriptor(desc);

    return MasterProcedureUtil
      .submitProcedure(new MasterProcedureUtil.NonceProcedureRunnable(this, nonceGroup, nonce) {
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

        @Override
        protected String getDescription() {
          return "CreateTableProcedure";
        }
      });
  }

};