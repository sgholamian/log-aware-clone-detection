//,temp,RpcProgramPortmap.java,123,140,temp,RpcProgramPortmap.java,89,98
//,3
public class xxx {
  private XDR getport(int xid, XDR in, XDR out) {
    PortmapMapping mapping = PortmapRequest.mapping(in);
    String key = PortmapMapping.key(mapping);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Portmap GETPORT key=" + key + " " + mapping);
    }
    PortmapMapping value = map.get(key);
    int res = 0;
    if (value != null) {
      res = value.getPort();
      if (LOG.isDebugEnabled()) {
        LOG.debug("Found mapping for key: " + key + " port:" + res);
      }
    } else {
      LOG.warn("Warning, no mapping for key: " + key);
    }
    return PortmapResponse.intReply(out, xid, res);
  }

};