//,temp,sample_39.java,2,17,temp,sample_38.java,2,16
//,3
public class xxx {
public void dummy_method(){
List<NodeId> retNodes = new ArrayList<>();
if (ResourceRequest.ANY.equals(resourceName)) {
retNodes.addAll(getAllNodeIds());
} else if (nodeNameToNodeMap.containsKey(resourceName)) {
retNodes.add(nodeNameToNodeMap.get(resourceName).getNodeID());
} else if (nodesPerRack.containsKey(resourceName)) {
for (N node : nodesPerRack.get(resourceName)) {
retNodes.add(node.getNodeID());
}
} else {


log.info("could not find a node matching given resourcename");
}
}

};