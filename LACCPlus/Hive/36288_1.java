//,temp,TestReplicationScenariosExternalTablesMetaDataOnly.java,559,570,temp,TestReplicationScenariosExternalTables.java,1073,1084
//,2
public class xxx {
      @Nullable
      @Override
      public Table apply(@Nullable Table table) {
        LOG.info("Performing injection on table " + table.getTableName());
        if (table.getTableName().equalsIgnoreCase("t1")){
          injectionPathCalled = true;
          return null;
        } else {
          nonInjectedPathCalled = true;
          return table;
        }
      }

};