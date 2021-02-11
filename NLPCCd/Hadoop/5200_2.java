//,temp,sample_5175.java,2,17,temp,sample_5174.java,2,13
//,3
public class xxx {
public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
RpcInfo info = (RpcInfo) e.getMessage();
RpcCall call = (RpcCall) info.header();
SocketAddress remoteAddress = info.remoteAddress();
if (LOG.isTraceEnabled()) {
}
if (this.progNumber != call.getProgram()) {


log.info("invalid rpc call program");
}
}

};