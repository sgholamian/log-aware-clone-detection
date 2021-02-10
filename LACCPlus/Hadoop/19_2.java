//,temp,RpcProgramMountd.java,177,185,temp,RpcProgramMountd.java,96,103
//,3
public class xxx {
  @Override
  public XDR nullOp(XDR out, int xid, InetAddress client) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("MOUNT NULLOP : " + " client: " + client);
    }
    return RpcAcceptedReply.getAcceptInstance(xid, new VerifierNone()).write(
        out);
  }

};