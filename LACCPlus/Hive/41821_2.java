//,temp,TestStatsReplicationScenarios.java,423,435,temp,TestReplicationScenariosAcrossInstances.java,1547,1557
//,3
public class xxx {
      @Override
      public Boolean apply(NotificationEvent entry) {
        if (entry.getEventType().equalsIgnoreCase(eventType) && entry.getTableName().equalsIgnoreCase(tbl)) {
          injectionPathCalled = true;
          LOG.warn("Verifier - DB: " + String.valueOf(entry.getDbName())
                  + " Table: " + String.valueOf(entry.getTableName())
                  + " Event: " + String.valueOf(entry.getEventType()));
          return false;
        }
        return true;
      }

};