//,temp,sample_2250.java,2,12,temp,sample_2633.java,2,10
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