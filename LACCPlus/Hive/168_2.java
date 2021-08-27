//,temp,ThriftCLIService.java,697,710,temp,ThriftCLIService.java,669,681
//,3
public class xxx {
  @Override
  public TGetTypeInfoResp GetTypeInfo(TGetTypeInfoReq req) throws TException {
    TGetTypeInfoResp resp = new TGetTypeInfoResp();
    try {
      OperationHandle operationHandle = cliService.getTypeInfo(new SessionHandle(req.getSessionHandle()));
      resp.setOperationHandle(operationHandle.toTOperationHandle());
      resp.setStatus(OK_STATUS);
    } catch (Exception e) {
      LOG.error("Failed to get type info [request: {}]", req, e);
      resp.setStatus(HiveSQLException.toTStatus(e));
    }
    return resp;
  }

};