//,temp,TestReplicationScenariosAcrossInstances.java,1323,1335,temp,TestReplicationScenariosAcrossInstances.java,1286,1299
//,3
public class xxx {
      @Override
      public Boolean apply(CallerArguments args) {
        injectionPathCalled = true;
        if (!args.dbName.equalsIgnoreCase(replicatedDbName) || (args.constraintTblName != null)) {
          LOG.warn("Verifier - DB: " + String.valueOf(args.dbName)
                  + " Constraint Table: " + String.valueOf(args.constraintTblName));
          return false;
        }
        if (args.tblName != null) {
          LOG.warn("Verifier - Table: " + String.valueOf(args.tblName));
          return args.tblName.equals("t1");
        }
        return true;
      }

};