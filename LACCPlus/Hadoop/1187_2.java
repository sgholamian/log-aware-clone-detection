//,temp,TestRMAppTransitions.java,144,156,temp,ResourceManager.java,833,845
//,3
public class xxx {
    @Override
    public void handle(RMNodeEvent event) {
      NodeId nodeId = event.getNodeId();
      RMNode node = this.rmContext.getRMNodes().get(nodeId);
      if (node != null) {
        try {
          ((EventHandler<RMNodeEvent>) node).handle(event);
        } catch (Throwable t) {
          LOG.error("Error in handling event type " + event.getType()
              + " for node " + nodeId, t);
        }
      }
    }

};