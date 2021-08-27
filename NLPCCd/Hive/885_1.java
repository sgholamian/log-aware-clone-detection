//,temp,sample_1372.java,2,13,temp,sample_1375.java,2,13
//,3
public class xxx {
public TGetTablesResp GetTables(TGetTablesReq req) throws TException {
TGetTablesResp resp = new TGetTablesResp();
try {
OperationHandle opHandle = cliService .getTables(new SessionHandle(req.getSessionHandle()), req.getCatalogName(), req.getSchemaName(), req.getTableName(), req.getTableTypes());
resp.setOperationHandle(opHandle.toTOperationHandle());
resp.setStatus(OK_STATUS);
} catch (Exception e) {


log.info("error getting tables");
}
}

};