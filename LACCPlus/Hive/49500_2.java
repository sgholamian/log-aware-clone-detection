//,temp,GetColumnsOperation.java,125,136,temp,GetFunctionsOperation.java,74,84
//,3
public class xxx {
  public GetFunctionsOperation(HiveSession parentSession, String catalogName, String schemaName,
      String functionName) {
    super(parentSession, OperationType.GET_FUNCTIONS);
    this.catalogName = catalogName;
    this.schemaName = schemaName;
    this.functionName = functionName;
    this.rowSet = RowSetFactory.create(RESULT_SET_SCHEMA, getProtocolVersion(), false);
    LOG.info(
        "Starting GetFunctionsOperation with the following parameters: catalogName={}, schemaName={}, functionName={}",
        catalogName, schemaName, functionName);
  }

};