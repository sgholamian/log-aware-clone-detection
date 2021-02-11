//,temp,sample_5077.java,2,13,temp,sample_7844.java,2,13
//,2
public class xxx {
public void handle(HttpExchange t) throws IOException {
try {
long startTick = System.currentTimeMillis();
doHandle(t);
} catch (IOException e) {
throw e;
} catch (Throwable e) {


log.info("unexpected exception");
}
}

};