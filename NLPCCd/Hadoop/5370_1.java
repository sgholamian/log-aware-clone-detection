//,temp,sample_9206.java,2,13,temp,sample_9207.java,2,14
//,2
public class xxx {
protected void populateNMTokens(List<NMToken> nmTokens) {
for (NMToken token : nmTokens) {
String nodeId = token.getNodeId().toString();
if (LOG.isDebugEnabled()) {
if (getNMTokenCache().containsToken(nodeId)) {


log.info("replacing token for");
}
}
}
}

};