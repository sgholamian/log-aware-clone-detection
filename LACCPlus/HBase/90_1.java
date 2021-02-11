//,temp,BackupSystemTable.java,1816,1824,temp,BackupSystemTable.java,1806,1814
//,3
public class xxx {
  public void updateProcessedTablesForMerge(List<TableName> tables) throws IOException {
    if (LOG.isTraceEnabled()) {
      LOG.trace("Update tables for merge : " + StringUtils.join(tables, ","));
    }
    Put put = createPutForUpdateTablesForMerge(tables);
    try (Table table = connection.getTable(tableName)) {
      table.put(put);
    }
  }

};