//,temp,sample_6521.java,2,12,temp,sample_1729.java,2,12
//,2
public class xxx {
public void messageReceived(ChannelHandlerContext ctx, MessageEvent messageEvent) throws Exception {
messageReceived = true;
if (LOG.isTraceEnabled()) {
}
ChannelHandler handler = ctx.getPipeline().get("timeout");
if (handler != null) {


log.info("removing timeout channel as we received message");
}
}

};