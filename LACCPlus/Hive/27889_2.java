//,temp,TestReplicationScenarios.java,934,992,temp,TestReplicationScenarios.java,865,932
//,3
public class xxx {
  @Test
  public void testBootstrapWithConcurrentRename() throws IOException {
    String name = testName.getMethodName();
    String dbName = createDB(name, driver);
    String replDbName = dbName + "_dupe";
    run("CREATE TABLE " + dbName + ".ptned(a string) partitioned by (b int) STORED AS TEXTFILE", driver);

    String[] ptn_data = new String[]{ "eleven" , "twelve" };
    String ptn_locn = new Path(TEST_PATH, name + "_ptn").toUri().getPath();

    createTestDataFile(ptn_locn, ptn_data);
    run("LOAD DATA LOCAL INPATH '" + ptn_locn + "' OVERWRITE INTO TABLE " + dbName + ".ptned PARTITION(b=1)", driver);

    BehaviourInjection<Table,Table> ptnedTableRenamer = new BehaviourInjection<Table,Table>(){
      @Nullable
      @Override
      public Table apply(@Nullable Table table) {
        if (injectionPathCalled) {
          nonInjectedPathCalled = true;
        } else {
          // getTable is invoked after fetching the table names
          injectionPathCalled = true;
          Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
              LOG.info("Entered new thread");
              IDriver driver2 = DriverFactory.newDriver(hconf);
              SessionState.start(new CliSessionState(hconf));
              try {
                driver2.run("ALTER TABLE " + dbName + ".ptned PARTITION (b=1) RENAME TO PARTITION (b=10)");
                driver2.run("ALTER TABLE " + dbName + ".ptned RENAME TO " + dbName + ".ptned_renamed");
              } catch (CommandProcessorException e) {
                throw new RuntimeException(e);
              }
              LOG.info("Exit new thread success");
            }
          });
          t.start();
          LOG.info("Created new thread {}", t.getName());
          try {
            t.join();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
        return table;
      }
    };
    InjectableBehaviourObjectStore.setGetTableBehaviour(ptnedTableRenamer);
    try {
      // The intermediate rename would've failed as bootstrap dump in progress
      bootstrapLoadAndVerify(dbName, replDbName);
      ptnedTableRenamer.assertInjectionsPerformed(true,true);
    } finally {
      InjectableBehaviourObjectStore.resetGetTableBehaviour(); // reset the behaviour
    }

    // The ptned table should be there in both source and target as rename was not successful
    verifyRun("SELECT a from " + dbName + ".ptned WHERE (b=1) ORDER BY a", ptn_data, driver);
    verifyRun("SELECT a from " + replDbName + ".ptned WHERE (b=1) ORDER BY a", ptn_data, driverMirror);

    // Verify if Rename after bootstrap is successful
    run("ALTER TABLE " + dbName + ".ptned PARTITION (b=1) RENAME TO PARTITION (b=10)", driver);
    verifyIfPartitionNotExist(dbName, "ptned", new ArrayList<>(Arrays.asList("1")), metaStoreClient);
    run("ALTER TABLE " + dbName + ".ptned RENAME TO " + dbName + ".ptned_renamed", driver);
    verifyIfTableNotExist(dbName, "ptned", metaStoreClient);
    verifyRun("SELECT a from " + dbName + ".ptned_renamed WHERE (b=10) ORDER BY a", ptn_data, driver);
  }

};