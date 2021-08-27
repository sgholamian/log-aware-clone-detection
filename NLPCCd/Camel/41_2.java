//,temp,sample_1724.java,2,8,temp,sample_1728.java,2,9
//,3
public class xxx {
protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
messageReceived = true;
if (LOG.isTraceEnabled()) {


log.info("message received");
}
}

};