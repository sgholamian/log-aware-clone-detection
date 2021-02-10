//,temp,HdfsWriter.java,73,82,temp,WebHdfsHandler.java,185,191
//,3
public class xxx {
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    LOG.debug("Error ", cause);
    resp = ExceptionHandler.exceptionCaught(cause);
    resp.headers().set(CONNECTION, CLOSE);
    ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
  }

};