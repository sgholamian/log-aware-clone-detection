//,temp,NettyRpcServerRequestDecoder.java,73,79,temp,NettyRpcServerRequestDecoder.java,65,71
//,3
public class xxx {
  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    allChannels.remove(ctx.channel());
    NettyRpcServer.LOG.trace("Disconnection {}; # active connections={}",
        ctx.channel().remoteAddress(), (allChannels.size() - 1));
    super.channelInactive(ctx);
  }

};