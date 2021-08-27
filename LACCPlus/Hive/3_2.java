//,temp,TestReplicationScenariosExternalTables.java,966,975,temp,TestReplicationScenariosAcrossInstances.java,1447,1457
//,3
public class xxx {
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