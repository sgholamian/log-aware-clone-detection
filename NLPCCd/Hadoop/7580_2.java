//,temp,sample_39.java,2,17,temp,sample_38.java,2,16
//,3
public class xxx {
public List<N> getNodesByResourceName(final String resourceName) {
Preconditions.checkArgument( resourceName != null && !resourceName.isEmpty());
List<N> retNodes = new ArrayList<>();
if (ResourceRequest.ANY.equals(resourceName)) {
retNodes.addAll(getAllNodes());
} else if (nodeNameToNodeMap.containsKey(resourceName)) {
retNodes.add(nodeNameToNodeMap.get(resourceName));
} else if (nodesPerRack.containsKey(resourceName)) {
retNodes.addAll(nodesPerRack.get(resourceName));
} else {


log.info("could not find a node matching given resourcename");
}
}

};