//,temp,AuthUtil.java,132,140,temp,RemoteProcedureDispatcher.java,175,183
//,3
public class xxx {
  public void removeCompletedOperation(final TRemote key, RemoteProcedure rp) {
    BufferNode node = nodeMap.get(key);
    if (node == null) {
      LOG.warn("since no node for this key {}, we can't removed the finished remote procedure",
        key);
      return;
    }
    node.operationCompleted(rp);
  }

};