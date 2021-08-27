//,temp,ThriftCLIService.java,904,919,temp,ThriftCLIService.java,728,740
//,3
public class xxx {
  @Override
  public TGetPrimaryKeysResp GetPrimaryKeys(TGetPrimaryKeysReq req)
		throws TException {
    TGetPrimaryKeysResp resp = new TGetPrimaryKeysResp();
    try {
      OperationHandle opHandle = cliService.getPrimaryKeys(
      new SessionHandle(req.getSessionHandle()), req.getCatalogName(),
      req.getSchemaName(), req.getTableName());
      resp.setOperationHandle(opHandle.toTOperationHandle());
      resp.setStatus(OK_STATUS);
    } catch (Exception e) {
     LOG.error("Failed to get primary keys [request: {}]", req, e);
     resp.setStatus(HiveSQLException.toTStatus(e));
    }
    return resp;
  }

};