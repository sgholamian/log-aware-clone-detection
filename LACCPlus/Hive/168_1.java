//,temp,ThriftCLIService.java,697,710,temp,ThriftCLIService.java,669,681
//,3
public class xxx {
  @Override
  public TGetSchemasResp GetSchemas(TGetSchemasReq req) throws TException {
    TGetSchemasResp resp = new TGetSchemasResp();
    try {
      OperationHandle opHandle = cliService.getSchemas(
          new SessionHandle(req.getSessionHandle()), req.getCatalogName(), req.getSchemaName());
      resp.setOperationHandle(opHandle.toTOperationHandle());
      resp.setStatus(OK_STATUS);
    } catch (Exception e) {
      LOG.error("Failed to get schemas [request: {}]", req, e);
      resp.setStatus(HiveSQLException.toTStatus(e));
    }
    return resp;
  }

};