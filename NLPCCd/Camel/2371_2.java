//,temp,sample_6521.java,2,12,temp,sample_1729.java,2,12
//,2
public class xxx {
protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
messageReceived = true;
if (LOG.isTraceEnabled()) {
}
ChannelHandler handler = ctx.pipeline().get("timeout");
if (handler != null) {


log.info("removing timeout channel as we received message");
}
}

};