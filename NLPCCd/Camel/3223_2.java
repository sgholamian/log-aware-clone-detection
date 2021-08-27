//,temp,sample_2547.java,2,11,temp,sample_8264.java,2,9
//,3
public class xxx {
public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent messageEvent) throws Exception {
Object in = messageEvent.getMessage();
if (LOG.isDebugEnabled()) {


log.info("channel received body");
}
}

};