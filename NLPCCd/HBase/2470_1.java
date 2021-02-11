//,temp,sample_5105.java,2,17,temp,sample_5111.java,2,17
//,3
public class xxx {
public void dummy_method(){
verifyDenied(putAction, testGrantRevoke);
try {
grantGlobalUsingAccessControlClient(TEST_UTIL, systemUserConnection, userName, Permission.Action.WRITE);
} catch (Throwable e) {
}
verifyAllowed(getAction, testGrantRevoke);
verifyAllowed(putAction, testGrantRevoke);
try {
revokeGlobalUsingAccessControlClient(TEST_UTIL, systemUserConnection, userName, Permission.Action.READ, Permission.Action.WRITE);
} catch (Throwable e) {


log.info("error during call of accesscontrolclient revoke");
}
}

};