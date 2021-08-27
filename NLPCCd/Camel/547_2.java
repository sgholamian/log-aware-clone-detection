//,temp,sample_8154.java,2,11,temp,sample_4688.java,2,11
//,3
public class xxx {
public void closeConnection(String key, IRCConnection connection) {
try {
connection.doQuit();
connection.close();
} catch (Exception e) {


log.info("error during closing connection");
}
}

};