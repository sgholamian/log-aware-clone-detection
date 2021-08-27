//,temp,sample_2526.java,2,7,temp,sample_2525.java,2,7
//,2
public class xxx {
public void renewDelegationToken(SessionHandle sessionHandle, HiveAuthFactory authFactory, String tokenStr) throws HiveSQLException {
sessionManager.getSession(sessionHandle).renewDelegationToken(authFactory, tokenStr);


log.info("renewdelegationtoken");
}

};