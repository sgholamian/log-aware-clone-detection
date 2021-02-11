//,temp,RpcProgramMountd.java,204,212,temp,RpcProgramMountd.java,191,202
//,3
public class xxx {
  @Override
  public XDR umntall(XDR out, int xid, InetAddress client) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("MOUNT UMNTALL : " + " client: " + client);
    }
    mounts.clear();
    return RpcAcceptedReply.getAcceptInstance(xid, new VerifierNone()).write(
        out);
  }

};