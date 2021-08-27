//,temp,ThriftCLIService.java,742,759,temp,ThriftCLIService.java,712,726
//,2
public class xxx {
  @Override
  public TGetColumnsResp GetColumns(TGetColumnsReq req) throws TException {
    TGetColumnsResp resp = new TGetColumnsResp();
    try {
      OperationHandle opHandle = cliService.getColumns(
          new SessionHandle(req.getSessionHandle()),
          req.getCatalogName(),
          req.getSchemaName(),
          req.getTableName(),
          req.getColumnName());
      resp.setOperationHandle(opHandle.toTOperationHandle());
      resp.setStatus(OK_STATUS);
    } catch (Exception e) {
      LOG.error("Failed to get column types [request: {}]", req, e);
      resp.setStatus(HiveSQLException.toTStatus(e));
    }
    return resp;
  }

};