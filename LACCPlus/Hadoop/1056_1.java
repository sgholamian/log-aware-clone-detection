//,temp,CommonNodeLabelsManager.java,665,696,temp,CommonNodeLabelsManager.java,330,369
//,3
public class xxx {
  protected void checkReplaceLabelsOnNode(
      Map<NodeId, Set<String>> replaceLabelsToNode) throws IOException {
    if (null == replaceLabelsToNode || replaceLabelsToNode.isEmpty()) {
      return;
    }
    
    // check all labels being added existed
    Set<String> knownLabels = labelCollections.keySet();
    for (Entry<NodeId, Set<String>> entry : replaceLabelsToNode.entrySet()) {
      NodeId nodeId = entry.getKey();
      Set<String> labels = entry.getValue();
      
      // As in YARN-2694, we disable user add more than 1 labels on a same host
      if (labels.size() > 1) {
        String msg = String.format("%d labels specified on host=%s"
            + ", please note that we do not support specifying multiple"
            + " labels on a single host for now.", labels.size(),
            nodeId.getHost());
        LOG.error(msg);
        throw new IOException(msg);
      }
      
      if (!knownLabels.containsAll(labels)) {
        String msg =
            "Not all labels being replaced contained by known "
                + "label collections, please check" + ", new labels=["
                + StringUtils.join(labels, ",") + "]";
        LOG.error(msg);
        throw new IOException(msg);
      }
    }
  }

};