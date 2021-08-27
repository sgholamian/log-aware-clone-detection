//,temp,sample_7366.java,2,13,temp,sample_4360.java,2,12
//,3
public class xxx {
private void checkConnection() throws Exception {
if (!connection.isConnected()) {
try {
connection.connect();
} catch (SmackException e) {


log.info("connection to xmpp server failed will try to reconnect later again");
}
}
}

};