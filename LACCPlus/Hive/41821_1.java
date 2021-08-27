//,temp,TestStatsReplicationScenarios.java,423,435,temp,TestReplicationScenariosAcrossInstances.java,1547,1557
//,3
public class xxx {
      @Override
      public Boolean apply(NotificationEvent entry) {
        cntEvents++;
        if (entry.getEventType().equalsIgnoreCase(EventMessage.EventType.UPDATE_TABLE_COLUMN_STAT.toString()) &&
            cntEvents > 1) {
          injectionPathCalled = true;
          LOG.warn("Verifier - DB: " + entry.getDbName()
                  + " Table: " + entry.getTableName()
                  + " Event: " + entry.getEventType());
          return false;
        }
        return true;
      }

};