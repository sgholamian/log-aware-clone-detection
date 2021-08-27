//,temp,GetColumnsOperation.java,125,136,temp,GetFunctionsOperation.java,74,84
//,3
public class xxx {
  protected GetColumnsOperation(HiveSession parentSession, String catalogName, String schemaName,
      String tableName, String columnName) {
    super(parentSession, OperationType.GET_COLUMNS);
    this.catalogName = catalogName;
    this.schemaName = schemaName;
    this.tableName = tableName;
    this.columnName = columnName;
    this.rowSet = RowSetFactory.create(RESULT_SET_SCHEMA, getProtocolVersion(), false);
    LOG.info("Starting GetColumnsOperation with the following parameters: "
        + "catalogName={}, schemaName={}, tableName={}, columnName={}",
        catalogName, schemaName, tableName, columnName);
  }

};