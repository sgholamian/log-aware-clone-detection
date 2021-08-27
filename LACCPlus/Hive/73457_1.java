//,temp,TestReplicationScenariosAcidTablesBootstrap.java,69,100,temp,TestReplicationScenariosAcidTables.java,141,165
//,3
public class xxx {
  @Test
  public void testAcidTablesBootstrapDuringIncremental() throws Throwable {
    // Take a bootstrap dump without acid tables
    WarehouseInstance.Tuple bootstrapDump = prepareDataAndDump(primaryDbName,
            dumpWithoutAcidClause);
    LOG.info(testName.getMethodName() + ": loading dump without acid tables.");
    replica.load(replicatedDbName, primaryDbName);
    verifyLoadExecution(replicatedDbName, bootstrapDump.lastReplicationId, false);

    // Take a incremental dump with acid table bootstrap
    prepareIncAcidData(primaryDbName);
    prepareIncNonAcidData(primaryDbName);
    LOG.info(testName.getMethodName() + ": incremental dump and load dump with acid table bootstrap.");
    WarehouseInstance.Tuple incrementalDump = primary.run("use " + primaryDbName)
            .dump(primaryDbName, dumpWithAcidBootstrapClause);
    replica.load(replicatedDbName, primaryDbName);
    verifyIncLoad(replicatedDbName, incrementalDump.lastReplicationId);
    // Ckpt should be set on bootstrapped tables.
    String hiveDumpLocation = incrementalDump.dumpLocation + File.separator + ReplUtils.REPL_HIVE_BASE_DIR;
    replica.verifyIfCkptSetForTables(replicatedDbName, acidTableNames, hiveDumpLocation);

    // Take a second normal incremental dump after Acid table boostrap
    prepareInc2AcidData(primaryDbName, primary.hiveConf);
    prepareInc2NonAcidData(primaryDbName, primary.hiveConf);
    LOG.info(testName.getMethodName()
             + ": second incremental dump and load dump after incremental with acid table " +
            "bootstrap.");
    WarehouseInstance.Tuple inc2Dump = primary.run("use " + primaryDbName)
            .dump(primaryDbName);
    replica.load(replicatedDbName, primaryDbName);
    verifyInc2Load(replicatedDbName, inc2Dump.lastReplicationId);
  }

};