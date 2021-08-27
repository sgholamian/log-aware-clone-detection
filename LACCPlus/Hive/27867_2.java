//,temp,TestReplicationScenariosAcrossInstances.java,1397,1479,temp,TestReplicationScenariosAcrossInstances.java,210,293
//,3
public class xxx {
  @Test
  public void testBootstrapReplLoadRetryAfterFailureForFunctions() throws Throwable {
    String funcName1 = "f1";
    String funcName2 = "f2";
    WarehouseInstance.Tuple tuple = primary.run("use " + primaryDbName)
            .run("CREATE FUNCTION " + primaryDbName + "." + funcName1 +
                    " as 'hivemall.tools.string.StopwordUDF' " +
                    "using jar  'ivy://io.github.myui:hivemall:0.4.0-2'")
            .run("CREATE FUNCTION " + primaryDbName + "." + funcName2 +
                    " as 'hivemall.tools.string.SplitWordsUDF' "+
                    "using jar  'ivy://io.github.myui:hivemall:0.4.0-1'")
            .dump(primaryDbName);

    // Allow create function only on f1. Create should fail for the second function.
    BehaviourInjection<CallerArguments, Boolean> callerVerifier
            = new BehaviourInjection<CallerArguments, Boolean>() {
              @Override
              public Boolean apply(CallerArguments args) {
                injectionPathCalled = true;
                if (!args.dbName.equalsIgnoreCase(replicatedDbName)) {
                  LOG.warn("Verifier - DB: " + String.valueOf(args.dbName));
                  return false;
                }
                if (args.funcName != null) {
                  LOG.debug("Verifier - Function: " + String.valueOf(args.funcName));
                  return args.funcName.equals(funcName1);
                }
                return true;
              }
            };
    InjectableBehaviourObjectStore.setCallerVerifier(callerVerifier);

    // Trigger bootstrap dump which just creates function f1 but not f2
    List<String> withConfigs = Arrays.asList("'hive.repl.approx.max.load.tasks'='1'",
            "'hive.in.repl.test.files.sorted'='true'");
    try {
      replica.loadFailure(replicatedDbName, primaryDbName, withConfigs);
      callerVerifier.assertInjectionsPerformed(true, false);
    } finally {
      InjectableBehaviourObjectStore.resetCallerVerifier(); // reset the behaviour
    }

    // Verify that only f1 got loaded
    replica.run("use " + replicatedDbName)
            .run("repl status " + replicatedDbName)
            .verifyResult("null")
            .run("show functions like '" + replicatedDbName + "%'")
            .verifyResult(replicatedDbName + "." + funcName1);

    // Verify no calls to load f1 only f2.
    callerVerifier = new BehaviourInjection<CallerArguments, Boolean>() {
      @Override
      public Boolean apply(CallerArguments args) {
        injectionPathCalled = true;
        if (!args.dbName.equalsIgnoreCase(replicatedDbName)) {
          LOG.warn("Verifier - DB: " + String.valueOf(args.dbName));
          return false;
        }
        if (args.funcName != null) {
          LOG.debug("Verifier - Function: " + String.valueOf(args.funcName));
          return args.funcName.equals(funcName2);
        }
        return true;
      }
    };
    InjectableBehaviourObjectStore.setCallerVerifier(callerVerifier);

    try {
      // Retry with same dump with which it was already loaded should resume the bootstrap load.
      // This time, it completes by adding just the function f2
      replica.load(replicatedDbName, primaryDbName);
      callerVerifier.assertInjectionsPerformed(true, false);
    } finally {
      InjectableBehaviourObjectStore.resetCallerVerifier(); // reset the behaviour
    }

    // Verify that both the functions are available.
    replica.run("use " + replicatedDbName)
            .run("repl status " + replicatedDbName)
            .verifyResult(tuple.lastReplicationId)
            .run("show functions like '" + replicatedDbName +"%'")
            .verifyResults(new String[] {replicatedDbName + "." + funcName1,
                                         replicatedDbName +"." +funcName2});
  }

};