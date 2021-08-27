//,temp,TestReplicationScenarios.java,934,992,temp,TestReplicationScenarios.java,865,932
//,3
public class xxx {
  @Test
  public void testBootstrapWithDropPartitionedTable() throws IOException {
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
                driver2.run("DROP TABLE " + dbName + ".ptned");
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
    Tuple bootstrap = null;
    try {
      bootstrap = bootstrapLoadAndVerify(dbName, replDbName);
      ptnedTableRenamer.assertInjectionsPerformed(true,true);
    } finally {
      InjectableBehaviourObjectStore.resetGetTableBehaviour(); // reset the behaviour
    }

    incrementalLoadAndVerify(dbName, replDbName);
    verifyIfTableNotExist(replDbName, "ptned", metaStoreClientMirror);
  }

};