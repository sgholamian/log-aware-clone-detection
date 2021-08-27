//,temp,GetSchemasOperation.java,53,61,temp,GetPrimaryKeysOperation.java,75,85
//,3
public class xxx {
  public GetPrimaryKeysOperation(HiveSession parentSession,
      String catalogName, String schemaName, String tableName) {
    super(parentSession, OperationType.GET_FUNCTIONS);
    this.catalogName = catalogName;
    this.schemaName = schemaName;
    this.tableName = tableName;
    this.rowSet = RowSetFactory.create(RESULT_SET_SCHEMA, getProtocolVersion(), false);
    LOG.info(
        "Starting GetPrimaryKeysOperation with the following parameters: catalogName={}, schemaName={}, tableName={}",
        catalogName, schemaName, tableName);
  }

};