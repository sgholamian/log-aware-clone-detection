//,temp,sample_6520.java,2,9,temp,sample_6516.java,2,8
//,3
public class xxx {
public void messageReceived(ChannelHandlerContext ctx, MessageEvent messageEvent) throws Exception {
messageReceived = true;
if (LOG.isTraceEnabled()) {


log.info("message received");
}
}

};