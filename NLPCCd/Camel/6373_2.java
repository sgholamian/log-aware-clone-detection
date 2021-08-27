//,temp,sample_7622.java,2,16,temp,sample_7621.java,2,14
//,3
public class xxx {
public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
Attribute<HttpServerChannelHandler> attr = ctx.channel().attr(SERVER_HANDLER_KEY);
HttpServerChannelHandler handler = attr.get();
if (handler != null) {
handler.exceptionCaught(ctx, cause);
} else {
if (cause instanceof ClosedChannelException) {


log.info("channel already closed ignoring this exception");
}
}
}

};