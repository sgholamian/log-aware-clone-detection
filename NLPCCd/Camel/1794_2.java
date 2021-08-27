//,temp,sample_2517.java,2,16,temp,sample_6806.java,2,16
//,2
public class xxx {
protected void doStart() throws Exception {
if (connection == null) {
try {
connection = endpoint.createConnection();
} catch (SmackException e) {
if (endpoint.isTestConnectionOnStartup()) {
throw new RuntimeException("Could not establish connection to XMPP server: " + endpoint.getConnectionDescription(), e);
} else {


log.info("could not connect to xmpp server producer will attempt lazy connection when needed");
}
}
}
}

};