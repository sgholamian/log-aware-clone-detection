//,temp,sample_1373.java,2,13,temp,sample_1371.java,2,13
//,3
public class xxx {
public TGetSchemasResp GetSchemas(TGetSchemasReq req) throws TException {
TGetSchemasResp resp = new TGetSchemasResp();
try {
OperationHandle opHandle = cliService.getSchemas( new SessionHandle(req.getSessionHandle()), req.getCatalogName(), req.getSchemaName());
resp.setOperationHandle(opHandle.toTOperationHandle());
resp.setStatus(OK_STATUS);
} catch (Exception e) {


log.info("error getting schemas");
}
}

};