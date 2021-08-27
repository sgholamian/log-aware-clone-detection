//,temp,sample_3081.java,2,15,temp,sample_3080.java,2,11
//,3
public class xxx {
public Token<? extends TokenIdentifier> setConnectorInfoForInputAndOutput(AccumuloConnectionParameters params, Connector conn, Configuration conf) throws Exception {
AuthenticationToken token = getDelegationToken(conn);
try {
InputConfigurator.setConnectorInfo(AccumuloInputFormat.class, conf, params.getAccumuloUserName(), token);
} catch (IllegalStateException e) {


log.info("ignoring illegalargumentexception about re setting connector information");
}
}

};