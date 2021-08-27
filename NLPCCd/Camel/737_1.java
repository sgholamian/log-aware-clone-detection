//,temp,sample_7289.java,2,10,temp,sample_1640.java,2,11
//,3
public class xxx {
public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
if (consumer.isRunAllowed()) {
if (cause instanceof ClosedChannelException) {


log.info("channel already closed ignoring this exception");
}
}
}

};