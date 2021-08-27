//,temp,GetSchemasOperation.java,53,61,temp,GetPrimaryKeysOperation.java,75,85
//,3
public class xxx {
  protected GetSchemasOperation(HiveSession parentSession, String catalogName, String schemaName) {
    super(parentSession, OperationType.GET_SCHEMAS);
    this.catalogName = catalogName;
    this.schemaName = schemaName;
    this.rowSet = RowSetFactory.create(RESULT_SET_SCHEMA, getProtocolVersion(), false);
    LOG.info(
        "Starting GetSchemasOperation with the following parameters: catalogName={}, schemaName={}",
        catalogName, schemaName);
  }

};