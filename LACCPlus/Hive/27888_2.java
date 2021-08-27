//,temp,TestReplicationScenarios.java,795,863,temp,TestReplicationScenarios.java,718,793
//,3
public class xxx {
  @Test
  public void testBootstrapWithConcurrentDropTable() throws IOException {
    String name = testName.getMethodName();
    String dbName = createDB(name, driver);
    String replDbName = dbName + "_dupe";
    run("CREATE TABLE " + dbName + ".unptned(a string) STORED AS TEXTFILE", driver);
    run("CREATE TABLE " + dbName + ".ptned(a string) partitioned by (b int) STORED AS TEXTFILE", driver);

    String[] unptn_data = new String[]{ "eleven" , "twelve" };
    String[] ptn_data_1 = new String[]{ "thirteen", "fourteen", "fifteen"};
    String[] ptn_data_2 = new String[]{ "fifteen", "sixteen", "seventeen"};

    String unptn_locn = new Path(TEST_PATH, name + "_unptn").toUri().getPath();
    String ptn_locn_1 = new Path(TEST_PATH, name + "_ptn1").toUri().getPath();
    String ptn_locn_2 = new Path(TEST_PATH, name + "_ptn2").toUri().getPath();

    createTestDataFile(unptn_locn, unptn_data);
    createTestDataFile(ptn_locn_1, ptn_data_1);
    createTestDataFile(ptn_locn_2, ptn_data_2);

    run("LOAD DATA LOCAL INPATH '" + unptn_locn + "' OVERWRITE INTO TABLE " + dbName + ".unptned", driver);
    verifySetup("SELECT * from " + dbName + ".unptned", unptn_data, driver);
    run("LOAD DATA LOCAL INPATH '" + ptn_locn_1 + "' OVERWRITE INTO TABLE " + dbName + ".ptned PARTITION(b=1)", driver);
    verifySetup("SELECT a from " + dbName + ".ptned WHERE b=1", ptn_data_1, driver);
    run("LOAD DATA LOCAL INPATH '" + ptn_locn_2 + "' OVERWRITE INTO TABLE " + dbName + ".ptned PARTITION(b=2)", driver);
    verifySetup("SELECT a from " + dbName + ".ptned WHERE b=2", ptn_data_2, driver);

    advanceDumpDir();

    BehaviourInjection<Table,Table> ptnedTableNuller = new BehaviourInjection<Table,Table>(){
      @Nullable
      @Override
      public Table apply(@Nullable Table table) {
        LOG.info("Performing injection on table " + table.getTableName());
        if (table.getTableName().equalsIgnoreCase("ptned")){
          injectionPathCalled = true;
          return null;
        } else {
          nonInjectedPathCalled = true;
          return table;
        }
      }
    };
    InjectableBehaviourObjectStore.setGetTableBehaviour(ptnedTableNuller);
    try {
      // The ptned table will not be dumped as getTable will return null
      run("REPL DUMP " + dbName, driver);
      ptnedTableNuller.assertInjectionsPerformed(true,true);
    } finally {
      InjectableBehaviourObjectStore.resetGetTableBehaviour(); // reset the behaviour
    }

    String replDumpLocn = getResult(0, 0, driver);
    String replDumpId = getResult(0, 1, true, driver);
    LOG.info("Bootstrap-Dump: Dumped to {} with id {}", replDumpLocn, replDumpId);
    run("REPL LOAD " + dbName + " INTO " + replDbName, driverMirror);

    // The ptned table should miss in target as the table was marked virtually as dropped
    verifyRun("SELECT * from " + replDbName + ".unptned", unptn_data, driverMirror);
    verifyFail("SELECT a from " + replDbName + ".ptned WHERE b=1", driverMirror);
    verifyIfTableNotExist(replDbName + "", "ptned", metaStoreClient);

    // Verify if Drop table on a non-existing table is idempotent
    run("DROP TABLE " + dbName + ".ptned", driver);

    advanceDumpDir();
    run("REPL DUMP " + dbName, driver);
    String postDropReplDumpLocn = getResult(0,0, driver);
    String postDropReplDumpId = getResult(0,1,true,driver);
    LOG.info("Dumped to {} with id {}->{}", postDropReplDumpLocn, replDumpId, postDropReplDumpId);
    assert(run("REPL LOAD " + dbName + " INTO " + replDbName, true, driverMirror));

    verifyRun("SELECT * from " + replDbName + ".unptned", unptn_data, driverMirror);
    verifyIfTableNotExist(replDbName, "ptned", metaStoreClientMirror);
    verifyFail("SELECT a from " + replDbName + ".ptned WHERE b=1", driverMirror);
  }

};