//,temp,sample_6908.java,2,16,temp,sample_7924.java,2,16
//,3
public class xxx {
private synchronized void closeConnectionAndChannel() throws IOException {
if (channelPool != null) {
try {
channelPool.close();
channelPool = null;
} catch (Exception e) {
throw new IOException("Error closing channelPool", e);
}
}
if (conn != null) {


log.info("closing connection with timeout ms");
}
}

};