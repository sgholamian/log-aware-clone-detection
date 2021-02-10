//,temp,RpcProgramPortmap.java,123,140,temp,RpcProgramPortmap.java,89,98
//,3
public class xxx {
  private XDR set(int xid, XDR in, XDR out) {
    PortmapMapping mapping = PortmapRequest.mapping(in);
    String key = PortmapMapping.key(mapping);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Portmap set key=" + key);
    }

    map.put(key, mapping);
    return PortmapResponse.intReply(out, xid, mapping.getPort());
  }

};