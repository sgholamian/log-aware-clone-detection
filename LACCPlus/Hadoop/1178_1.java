//,temp,SimpleTcpClientHandler.java,40,48,temp,Nfs3Utils.java,124,136
//,3
public class xxx {
  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
    // Send the request
    if (LOG.isDebugEnabled()) {
      LOG.debug("sending PRC request");
    }
    ChannelBuffer outBuf = XDR.writeMessageTcp(request, true);
    e.getChannel().write(outBuf);
  }

};