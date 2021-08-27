//,temp,TestReplicationScenariosAcidTables.java,711,788,temp,TestReplicationScenariosAcidTables.java,638,709
//,3
public class xxx {
  @Test
  public void testAcidTablesBootstrapWithConcurrentWrites() throws Throwable {
    HiveConf primaryConf = primary.getConf();
    primary.run("use " + primaryDbName)
            .run("create table t1 (id int) clustered by(id) into 3 buckets stored as orc " +
                    "tblproperties (\"transactional\"=\"true\")")
            .run("insert into t1 values(1)");

    // Perform concurrent write on the acid table t1 when bootstrap dump in progress. Bootstrap
    // won't see the written data but the subsequent incremental repl should see it.
    BehaviourInjection<CallerArguments, Boolean> callerInjectedBehavior
            = new BehaviourInjection<CallerArguments, Boolean>() {
      @Nullable
      @Override
      public Boolean apply(@Nullable CallerArguments args) {
        if (injectionPathCalled) {
          nonInjectedPathCalled = true;
        } else {
          // Insert another row to t1 from another txn when bootstrap dump in progress.
          injectionPathCalled = true;
          Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
              LOG.info("Entered new thread");
              IDriver driver = DriverFactory.newDriver(primaryConf);
              SessionState.start(new CliSessionState(primaryConf));
              try {
                driver.run("insert into " + primaryDbName + ".t1 values(2)");
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
        return true;
      }
    };

    InjectableBehaviourObjectStore.setCallerVerifier(callerInjectedBehavior);
    WarehouseInstance.Tuple bootstrapDump = null;
    try {
      bootstrapDump = primary.dump(primaryDbName);
      callerInjectedBehavior.assertInjectionsPerformed(true, true);
    } finally {
      InjectableBehaviourObjectStore.resetCallerVerifier(); // reset the behaviour
    }

    // Bootstrap dump has taken snapshot before concurrent tread performed write. So, it won't see data "2".
    replica.load(replicatedDbName, primaryDbName)
            .run("use " + replicatedDbName)
            .run("repl status " + replicatedDbName)
            .verifyResult(bootstrapDump.lastReplicationId)
            .run("select id from t1 order by id")
            .verifyResults(new String[]{"1" });

    // Incremental should include the concurrent write of data "2" from another txn.
    WarehouseInstance.Tuple incrementalDump = primary.dump(primaryDbName);
    replica.load(replicatedDbName, primaryDbName)
            .run("use " + replicatedDbName)
            .run("repl status " + replicatedDbName)
            .verifyResult(incrementalDump.lastReplicationId)
            .run("select id from t1 order by id")
            .verifyResults(new String[]{"1", "2" });
  }

};