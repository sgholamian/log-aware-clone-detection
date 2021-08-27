//,temp,sample_650.java,2,12,temp,sample_6390.java,2,12
//,3
public class xxx {
public void stopServer() {
if (server != null) {
try {
server.stop();
} catch (Exception ex) {


log.info("server doesn t stop normal");
}
}
}

};