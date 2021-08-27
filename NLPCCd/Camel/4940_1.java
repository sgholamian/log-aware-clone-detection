//,temp,sample_442.java,2,13,temp,sample_443.java,2,17
//,3
public class xxx {
public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
HttpServerChannelHandler handler = (HttpServerChannelHandler) ctx.getAttachment();
if (handler != null) {
handler.exceptionCaught(ctx, e);
} else {
if (e.getCause() instanceof ClosedChannelException) {


log.info("channel already closed ignoring this exception");
}
}
}

};