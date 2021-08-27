//,temp,sample_1372.java,2,13,temp,sample_1375.java,2,13
//,3
public class xxx {
public TGetFunctionsResp GetFunctions(TGetFunctionsReq req) throws TException {
TGetFunctionsResp resp = new TGetFunctionsResp();
try {
OperationHandle opHandle = cliService.getFunctions( new SessionHandle(req.getSessionHandle()), req.getCatalogName(), req.getSchemaName(), req.getFunctionName());
resp.setOperationHandle(opHandle.toTOperationHandle());
resp.setStatus(OK_STATUS);
} catch (Exception e) {


log.info("error getting functions");
}
}

};