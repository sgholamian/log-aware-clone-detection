//,temp,sample_1725.java,2,14,temp,sample_683.java,2,8
//,3
public class xxx {
public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
if (LOG.isTraceEnabled()) {
}
if (exceptionHandled) {
return;
}
exceptionHandled = true;
if (LOG.isDebugEnabled()) {


log.info("closing channel as an exception was thrown from netty");
}
}

};