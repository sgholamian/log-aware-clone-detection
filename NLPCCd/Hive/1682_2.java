//,temp,sample_1381.java,2,13,temp,sample_1374.java,2,13
//,3
public class xxx {
public TGetColumnsResp GetColumns(TGetColumnsReq req) throws TException {
TGetColumnsResp resp = new TGetColumnsResp();
try {
OperationHandle opHandle = cliService.getColumns( new SessionHandle(req.getSessionHandle()), req.getCatalogName(), req.getSchemaName(), req.getTableName(), req.getColumnName());
resp.setOperationHandle(opHandle.toTOperationHandle());
resp.setStatus(OK_STATUS);
} catch (Exception e) {


log.info("error getting columns");
}
}

};