//,temp,sample_1360.java,2,16,temp,sample_1361.java,2,16
//,2
public class xxx {
public TCancelDelegationTokenResp CancelDelegationToken(TCancelDelegationTokenReq req) throws TException {
TCancelDelegationTokenResp resp = new TCancelDelegationTokenResp();
if (hiveAuthFactory == null || !hiveAuthFactory.isSASLKerberosUser()) {
resp.setStatus(unsecureTokenErrorStatus());
} else {
try {
cliService.cancelDelegationToken(new SessionHandle(req.getSessionHandle()), hiveAuthFactory, req.getDelegationToken());
resp.setStatus(OK_STATUS);
} catch (HiveSQLException e) {


log.info("error canceling delegation token");
}
}
}

};