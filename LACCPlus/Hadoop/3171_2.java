//,temp,CommonNodeLabelsManager.java,693,724,temp,CommonNodeLabelsManager.java,354,393
//,3
public class xxx {
  protected void checkAddLabelsToNode(
      Map<NodeId, Set<String>> addedLabelsToNode) throws IOException {
    if (null == addedLabelsToNode || addedLabelsToNode.isEmpty()) {
      return;
    }

    // check all labels being added existed
    Set<String> knownLabels = labelCollections.keySet();
    for (Entry<NodeId, Set<String>> entry : addedLabelsToNode.entrySet()) {
      NodeId nodeId = entry.getKey();
      Set<String> labels = entry.getValue();
      
      if (!knownLabels.containsAll(labels)) {
        String msg =
            "Not all labels being added contained by known "
                + "label collections, please check" + ", added labels=["
                + StringUtils.join(labels, ",") + "]";
        LOG.error(msg);
        throw new IOException(msg);
      }
      
      // In YARN-2694, we temporarily disable user add more than 1 labels on a
      // same host
      if (!labels.isEmpty()) {
        Set<String> newLabels = new HashSet<String>(getLabelsByNode(nodeId));
        newLabels.addAll(labels);
        // we don't allow number of labels on a node > 1 after added labels
        if (newLabels.size() > 1) {
          String msg =
              String.format(
                      "%d labels specified on host=%s after add labels to node"
                          + ", please note that we do not support specifying multiple"
                          + " labels on a single host for now.",
                      newLabels.size(), nodeId.getHost());
          LOG.error(msg);
          throw new IOException(msg);
        }
      }
    }
  }

};