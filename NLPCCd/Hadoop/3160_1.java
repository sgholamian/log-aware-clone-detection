//,temp,sample_9002.java,2,12,temp,sample_914.java,2,14
//,3
public class xxx {
public void joinHttpServer() {
if (httpServer != null) {
try {
httpServer.join();
} catch (InterruptedException e) {


log.info("caught interruptedexception joining namenodehttpserver");
}
}
}

};