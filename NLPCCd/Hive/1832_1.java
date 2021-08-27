//,temp,sample_1373.java,2,13,temp,sample_1371.java,2,13
//,3
public class xxx {
public TGetTableTypesResp GetTableTypes(TGetTableTypesReq req) throws TException {
TGetTableTypesResp resp = new TGetTableTypesResp();
try {
OperationHandle opHandle = cliService.getTableTypes(new SessionHandle(req.getSessionHandle()));
resp.setOperationHandle(opHandle.toTOperationHandle());
resp.setStatus(OK_STATUS);
} catch (Exception e) {


log.info("error getting table types");
}
}

};