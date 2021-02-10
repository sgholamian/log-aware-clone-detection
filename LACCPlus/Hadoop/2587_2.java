//,temp,TestDataNodeInitStorage.java,65,70,temp,BlockReportLeaseManager.java,217,225
//,3
public class xxx {
  public synchronized void unregister(DatanodeDescriptor dn) {
    NodeData node = nodes.remove(dn.getDatanodeUuid());
    if (node == null) {
      LOG.info("Can't unregister DN {} because it is not currently " +
          "registered.", dn.getDatanodeUuid());
      return;
    }
    remove(node);
  }

};