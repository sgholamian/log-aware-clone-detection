//,temp,TestReplicationScenariosAcidTables.java,895,908,temp,TestReplicationScenariosAcrossInstances.java,261,273
//,3
public class xxx {
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