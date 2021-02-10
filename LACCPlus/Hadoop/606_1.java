//,temp,Nfs3Utils.java,138,144,temp,Nfs3Utils.java,124,136
//,3
public class xxx {
  public static void writeChannelCommit(Channel channel, XDR out, int xid) {
    if (RpcProgramNfs3.LOG.isDebugEnabled()) {
      RpcProgramNfs3.LOG.debug("Commit done:" + xid);
    }
    ChannelBuffer outBuf = XDR.writeMessageTcp(out, true);
    channel.write(outBuf);
  }

};