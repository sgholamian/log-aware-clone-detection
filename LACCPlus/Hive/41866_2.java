//,temp,ThriftCLIService.java,904,919,temp,ThriftCLIService.java,728,740
//,3
public class xxx {
  @Override
  public TGetTableTypesResp GetTableTypes(TGetTableTypesReq req) throws TException {
    TGetTableTypesResp resp = new TGetTableTypesResp();
    try {
      OperationHandle opHandle = cliService.getTableTypes(new SessionHandle(req.getSessionHandle()));
      resp.setOperationHandle(opHandle.toTOperationHandle());
      resp.setStatus(OK_STATUS);
    } catch (Exception e) {
      LOG.error("Failed to get table types [request: {}]", req, e);
      resp.setStatus(HiveSQLException.toTStatus(e));
    }
    return resp;
  }

};