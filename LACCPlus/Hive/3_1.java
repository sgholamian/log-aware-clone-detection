//,temp,TestReplicationScenariosExternalTables.java,966,975,temp,TestReplicationScenariosAcrossInstances.java,1447,1457
//,3
public class xxx {
      @Nullable
      @Override
      public Boolean apply(@Nullable CallerArguments args) {
        if (args.tblName.equalsIgnoreCase("t4") && args.dbName.equalsIgnoreCase(replicatedDbName)) {
          injectionPathCalled = true;
          LOG.warn("Verifier - DB : " + args.dbName + " TABLE : " + args.tblName);
          return false;
        }
        return true;
      }

};