//,temp,sample_1377.java,2,12,temp,sample_1378.java,2,12
//,2
public class xxx {
public TCloseOperationResp CloseOperation(TCloseOperationReq req) throws TException {
TCloseOperationResp resp = new TCloseOperationResp();
try {
cliService.closeOperation(new OperationHandle(req.getOperationHandle()));
resp.setStatus(OK_STATUS);
} catch (Exception e) {


log.info("error closing operation");
}
}

};