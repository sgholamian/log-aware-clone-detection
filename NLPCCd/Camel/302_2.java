//,temp,sample_7290.java,2,11,temp,sample_1639.java,2,10
//,3
public class xxx {
public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent exceptionEvent) throws Exception {
if (consumer.isRunAllowed()) {
if (exceptionEvent.getCause() instanceof ClosedChannelException) {


log.info("channel already closed ignoring this exception");
}
}
}

};