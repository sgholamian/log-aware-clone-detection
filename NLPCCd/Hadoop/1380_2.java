//,temp,sample_253.java,2,11,temp,sample_5325.java,2,11
//,3
public class xxx {
private BlockReaderPeer nextTcpPeer() throws IOException {
if (remainingCacheTries > 0) {
Peer peer = clientContext.getPeerCache().get(datanode, false);
if (peer != null) {


log.info("nexttcppeer reusing existing peer");
}
}
}

};