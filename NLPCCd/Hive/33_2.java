//,temp,sample_2513.java,2,7,temp,sample_2524.java,2,7
//,2
public class xxx {
public String getDelegationToken(SessionHandle sessionHandle, HiveAuthFactory authFactory, String owner, String renewer) throws HiveSQLException {
String delegationToken = sessionManager.getSession(sessionHandle). getDelegationToken(authFactory, owner, renewer);


log.info("getdelegationtoken owner renewer");
}

};