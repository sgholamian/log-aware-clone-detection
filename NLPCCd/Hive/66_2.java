//,temp,sample_1360.java,2,16,temp,sample_1361.java,2,16
//,2
public class xxx {
public TRenewDelegationTokenResp RenewDelegationToken(TRenewDelegationTokenReq req) throws TException {
TRenewDelegationTokenResp resp = new TRenewDelegationTokenResp();
if (hiveAuthFactory == null || !hiveAuthFactory.isSASLKerberosUser()) {
resp.setStatus(unsecureTokenErrorStatus());
} else {
try {
cliService.renewDelegationToken(new SessionHandle(req.getSessionHandle()), hiveAuthFactory, req.getDelegationToken());
resp.setStatus(OK_STATUS);
} catch (HiveSQLException e) {


log.info("error obtaining renewing token");
}
}
}

};