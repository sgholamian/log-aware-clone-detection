//,temp,sample_711.java,2,9,temp,sample_8839.java,2,9
//,2
public class xxx {
protected void removeStoredToken(TokenIdent ident) throws IOException {
String nodeRemovePath = getNodePath(ZK_DTSM_TOKENS_ROOT, DELEGATION_TOKEN_PREFIX + ident.getSequenceNumber());
if (LOG.isDebugEnabled()) {


log.info("removing zkdtsmdelegationtoken");
}
}

};