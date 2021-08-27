//,temp,sample_3073.java,2,10,temp,sample_3072.java,2,10
//,2
public class xxx {
public void setInputFormatConnectorInfo(JobConf conf, String username, AuthenticationToken token) throws AccumuloSecurityException {
try {
AccumuloInputFormat.setConnectorInfo(conf, username, token);
} catch (IllegalStateException e) {


log.info("ignoring exception setting accumulo connector instance for user");
}
}

};