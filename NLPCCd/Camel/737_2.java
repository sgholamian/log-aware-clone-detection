//,temp,sample_7289.java,2,10,temp,sample_1640.java,2,11
//,3
public class xxx {
public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent exceptionEvent) throws Exception {
if (consumer.isRunAllowed()) {
if (exceptionEvent.getCause() instanceof ClosedChannelException) {
} else {


log.info("closing channel as an exception was thrown from netty");
}
}
}

};