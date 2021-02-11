//,temp,sample_5470.java,2,9,temp,sample_5471.java,2,12
//,3
public class xxx {
public synchronized long requestLease(DatanodeDescriptor dn) {
NodeData node = nodes.get(dn.getDatanodeUuid());
if (node == null) {
node = registerNode(dn);
}
if (node.leaseId != 0) {


log.info("removing existing br lease for dn in order to issue a new one");
}
}

};