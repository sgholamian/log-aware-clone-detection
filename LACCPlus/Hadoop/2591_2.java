//,temp,RpcProgramMountd.java,204,212,temp,RpcProgramMountd.java,191,202
//,3
public class xxx {
  @Override
  public XDR umnt(XDR xdr, XDR out, int xid, InetAddress client) {
    String path = xdr.readString();
    if (LOG.isDebugEnabled()) {
      LOG.debug("MOUNT UMNT path: " + path + " client: " + client);
    }
    
    String host = client.getHostName();
    mounts.remove(new MountEntry(host, path));
    RpcAcceptedReply.getAcceptInstance(xid, new VerifierNone()).write(out);
    return out;
  }

};