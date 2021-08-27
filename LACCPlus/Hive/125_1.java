//,temp,TestReplicationScenariosAcrossInstances.java,1323,1335,temp,TestReplicationScenariosAcrossInstances.java,1286,1299
//,3
public class xxx {
      @Override
      public Boolean apply(CallerArguments args) {
        injectionPathCalled = true;
        if (!args.dbName.equalsIgnoreCase(replicatedDbName) || (args.funcName != null)) {
          LOG.warn("Verifier - DB: " + String.valueOf(args.dbName) + " Func: " + String.valueOf(args.funcName));
          return false;
        }
        if (args.constraintTblName != null) {
          LOG.warn("Verifier - Constraint Table: " + String.valueOf(args.constraintTblName));
          return (args.constraintTblName.equals("t1") || args.constraintTblName.equals("t3"));
        }
        return true;
      }

};