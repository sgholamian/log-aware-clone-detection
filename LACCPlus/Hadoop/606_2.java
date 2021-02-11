//,temp,Nfs3Utils.java,138,144,temp,Nfs3Utils.java,124,136
//,3
public class xxx {
  public static void writeChannel(Channel channel, XDR out, int xid) {
    if (channel == null) {
      RpcProgramNfs3.LOG
          .info("Null channel should only happen in tests. Do nothing.");
      return;
    }
    
    if (RpcProgramNfs3.LOG.isDebugEnabled()) {
      RpcProgramNfs3.LOG.debug(WRITE_RPC_END + xid);
    }
    ChannelBuffer outBuf = XDR.writeMessageTcp(out, true);
    channel.write(outBuf);
  }

};