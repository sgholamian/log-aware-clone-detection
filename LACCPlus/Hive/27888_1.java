//,temp,TestReplicationScenarios.java,795,863,temp,TestReplicationScenarios.java,718,793
//,3
public class xxx {
  @Test
  public void testBootstrapWithConcurrentDropPartition() throws IOException {
    String name = testName.getMethodName();
    String dbName = createDB(name, driver);
    String replDbName = dbName + "_dupe";
    run("CREATE TABLE " + dbName + ".ptned(a string) partitioned by (b int) STORED AS TEXTFILE", driver);

    String[] ptn_data_1 = new String[]{ "thirteen", "fourteen", "fifteen"};
    String[] ptn_data_2 = new String[]{ "fifteen", "sixteen", "seventeen"};
    String[] empty = new String[]{};

    String ptn_locn_1 = new Path(TEST_PATH, name + "_ptn1").toUri().getPath();
    String ptn_locn_2 = new Path(TEST_PATH, name + "_ptn2").toUri().getPath();

    createTestDataFile(ptn_locn_1, ptn_data_1);
    createTestDataFile(ptn_locn_2, ptn_data_2);

    run("LOAD DATA LOCAL INPATH '" + ptn_locn_1 + "' OVERWRITE INTO TABLE " + dbName + ".ptned PARTITION(b=1)", driver);
    verifySetup("SELECT a from " + dbName + ".ptned WHERE b=1", ptn_data_1, driver);
    run("LOAD DATA LOCAL INPATH '" + ptn_locn_2 + "' OVERWRITE INTO TABLE " + dbName + ".ptned PARTITION(b=2)", driver);
    verifySetup("SELECT a from " + dbName + ".ptned WHERE b=2", ptn_data_2, driver);

    advanceDumpDir();

    BehaviourInjection<List<String>, List<String>> listPartitionNamesNuller
            = new BehaviourInjection<List<String>, List<String>>(){
      @Nullable
      @Override
      public List<String> apply(@Nullable List<String> partitions) {
        injectionPathCalled = true;
        return new ArrayList<String>();
      }
    };
    InjectableBehaviourObjectStore.setListPartitionNamesBehaviour(listPartitionNamesNuller);
    try {
      // None of the partitions will be dumped as the partitions list was empty
      run("REPL DUMP " + dbName, driver);
      listPartitionNamesNuller.assertInjectionsPerformed(true, false);
    } finally {
      InjectableBehaviourObjectStore.resetListPartitionNamesBehaviour(); // reset the behaviour
    }

    String replDumpLocn = getResult(0, 0, driver);
    String replDumpId = getResult(0, 1, true, driver);
    LOG.info("Bootstrap-Dump: Dumped to {} with id {}", replDumpLocn, replDumpId);
    run("REPL LOAD " + dbName + " INTO " + replDbName, driverMirror);

    // All partitions should miss in target as it was marked virtually as dropped
    verifyRun("SELECT a from " + replDbName + ".ptned WHERE b=1", empty, driverMirror);
    verifyRun("SELECT a from " + replDbName + ".ptned WHERE b=2", empty, driverMirror);
    verifyIfPartitionNotExist(replDbName , "ptned", new ArrayList<>(Arrays.asList("1")), metaStoreClientMirror);
    verifyIfPartitionNotExist(replDbName, "ptned", new ArrayList<>(Arrays.asList("2")), metaStoreClientMirror);

    // Verify if drop partition on a non-existing partition is idempotent and just a noop.
    run("ALTER TABLE " + dbName + ".ptned DROP PARTITION (b=1)", driver);
    run("ALTER TABLE " + dbName + ".ptned DROP PARTITION (b=2)", driver);

    advanceDumpDir();
    run("REPL DUMP " + dbName, driver);
    String postDropReplDumpLocn = getResult(0,0,driver);
    String postDropReplDumpId = getResult(0,1,true,driver);
    LOG.info("Dumped to {} with id {}->{}", postDropReplDumpLocn, replDumpId, postDropReplDumpId);
    assert(run("REPL LOAD " + dbName + " INTO " + replDbName, true, driverMirror));

    verifyIfPartitionNotExist(replDbName, "ptned", new ArrayList<>(Arrays.asList("1")), metaStoreClientMirror);
    verifyIfPartitionNotExist(replDbName, "ptned", new ArrayList<>(Arrays.asList("2")), metaStoreClientMirror);
    verifyRun("SELECT a from " + replDbName + ".ptned WHERE b=1", empty, driverMirror);
    verifyRun("SELECT a from " + replDbName + ".ptned WHERE b=2", empty, driverMirror);
  }

};