//,temp,sample_1948.java,2,18,temp,sample_1358.java,2,17
//,3
public class xxx {
public synchronized void stop() {
if (isStarted && !isEmbedded) {
if(server != null) {
server.stop();
}
if((httpServer != null) && httpServer.isStarted()) {
try {
httpServer.stop();
} catch (Exception e) {


log.info("error stopping http server");
}
}
}
}

};