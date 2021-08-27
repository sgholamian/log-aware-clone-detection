//,temp,sample_1377.java,2,12,temp,sample_1378.java,2,12
//,2
public class xxx {
public TCancelOperationResp CancelOperation(TCancelOperationReq req) throws TException {
TCancelOperationResp resp = new TCancelOperationResp();
try {
cliService.cancelOperation(new OperationHandle(req.getOperationHandle()));
resp.setStatus(OK_STATUS);
} catch (Exception e) {


log.info("error cancelling operation");
}
}

};