//,temp,NettyRpcServerRequestDecoder.java,73,79,temp,NettyRpcServerRequestDecoder.java,65,71
//,3
public class xxx {
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
    allChannels.remove(ctx.channel());
    NettyRpcServer.LOG.trace("Connection {}; caught unexpected downstream exception.",
        ctx.channel().remoteAddress(), e.getCause());
    ctx.channel().close();
  }

};