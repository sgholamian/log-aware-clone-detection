//,temp,sample_1381.java,2,13,temp,sample_1374.java,2,13
//,3
public class xxx {
public TGetPrimaryKeysResp GetPrimaryKeys(TGetPrimaryKeysReq req) throws TException {
TGetPrimaryKeysResp resp = new TGetPrimaryKeysResp();
try {
OperationHandle opHandle = cliService.getPrimaryKeys( new SessionHandle(req.getSessionHandle()), req.getCatalogName(), req.getSchemaName(), req.getTableName());
resp.setOperationHandle(opHandle.toTOperationHandle());
resp.setStatus(OK_STATUS);
} catch (Exception e) {


log.info("error getting functions");
}
}

};