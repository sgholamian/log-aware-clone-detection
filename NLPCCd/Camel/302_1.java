//,temp,sample_7290.java,2,11,temp,sample_1639.java,2,10
//,3
public class xxx {
public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
if (consumer.isRunAllowed()) {
if (cause instanceof ClosedChannelException) {
} else {


log.info("closing channel as an exception was thrown from netty");
}
}
}

};