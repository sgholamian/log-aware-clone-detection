//,temp,TestReplicationScenariosAcrossInstances.java,1397,1479,temp,TestReplicationScenariosAcrossInstances.java,210,293
//,3
public class xxx {
  @Test
  public void testBootstrapReplLoadRetryAfterFailureForPartitions() throws Throwable {
    WarehouseInstance.Tuple tuple = primary
            .run("use " + primaryDbName)
            .run("create table t2 (place string) partitioned by (country string)")
            .run("insert into table t2 partition(country='india') values ('bangalore')")
            .run("insert into table t2 partition(country='uk') values ('london')")
            .run("insert into table t2 partition(country='us') values ('sfo')")
            .run("CREATE FUNCTION " + primaryDbName
                    + ".testFunctionOne as 'hivemall.tools.string.StopwordUDF' "
                    + "using jar  'ivy://io.github.myui:hivemall:0.4.0-2'")
            .dump(primaryDbName);

    // Inject a behavior where REPL LOAD failed when try to load table "t2" and partition "uk".
    // So, table "t2" will exist and partition "india" will exist, rest failed as operation failed.
    BehaviourInjection<List<Partition>, Boolean> addPartitionStub
            = new BehaviourInjection<List<Partition>, Boolean>() {
      @Override
      public Boolean apply(List<Partition> ptns) {
        for (Partition ptn : ptns) {
          if (ptn.getValues().get(0).equals("uk")) {
            injectionPathCalled = true;
            LOG.warn("####getPartition Stub called");
            return false;
          }
        }
        return true;
      }
    };
    InjectableBehaviourObjectStore.setAddPartitionsBehaviour(addPartitionStub);

    // Make sure that there's some order in which the objects are loaded.
    List<String> withConfigs = Arrays.asList("'hive.repl.approx.max.load.tasks'='1'",
            "'hive.in.repl.test.files.sorted'='true'",
      "'" + HiveConf.ConfVars.REPL_LOAD_PARTITIONS_WITH_DATA_COPY_BATCH_SIZE + "' = '1'");
    replica.loadFailure(replicatedDbName, primaryDbName, withConfigs);
    InjectableBehaviourObjectStore.resetAddPartitionModifier(); // reset the behaviour
    addPartitionStub.assertInjectionsPerformed(true, false);

    replica.run("use " + replicatedDbName)
            .run("repl status " + replicatedDbName)
            .verifyResult("null")
            .run("show tables")
            .verifyResults(new String[] {"t2" })
            .run("select country from t2 order by country")
            .verifyResults(Collections.singletonList("india"));

    // Verify if no create table calls. Add partitions and create function calls expected.
    BehaviourInjection<CallerArguments, Boolean> callerVerifier
            = new BehaviourInjection<CallerArguments, Boolean>() {
      @Nullable
      @Override
      public Boolean apply(@Nullable CallerArguments args) {
        if (!args.dbName.equalsIgnoreCase(replicatedDbName) || (args.tblName != null)) {
          injectionPathCalled = true;
          LOG.warn("Verifier - DB: " + String.valueOf(args.dbName)
                  + " Table: " + String.valueOf(args.tblName));
          return false;
        }
        return true;
      }
    };
    InjectableBehaviourObjectStore.setCallerVerifier(callerVerifier);

    try {
      // Retry with same dump with which it was already loaded should resume the bootstrap load.
      // This time, it completes by adding remaining partitions and function.
      replica.load(replicatedDbName, primaryDbName);
      callerVerifier.assertInjectionsPerformed(false, false);
    } finally {
      InjectableBehaviourObjectStore.resetCallerVerifier(); // reset the behaviour
    }

    replica.run("use " + replicatedDbName)
            .run("repl status " + replicatedDbName)
            .verifyResult(tuple.lastReplicationId)
            .run("show tables")
            .verifyResults(new String[] { "t2" })
            .run("select country from t2 order by country")
            .verifyResults(Arrays.asList("india", "uk", "us"))
            .run("show functions like '" + replicatedDbName + "%'")
            .verifyResult(replicatedDbName + ".testFunctionOne");
  }

};