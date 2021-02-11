//,temp,sample_6555.java,2,13,temp,sample_7844.java,2,13
//,2
public class xxx {
public void handle(HttpExchange t) throws IOException {
try {
long startTick = System.currentTimeMillis();
doHandle(t);
} catch (IOException e) {
throw e;
} catch (IllegalArgumentException e) {


log.info("exception");
}
}

};