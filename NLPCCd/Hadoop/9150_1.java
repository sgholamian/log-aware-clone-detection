//,temp,sample_5470.java,2,9,temp,sample_5991.java,2,9
//,3
public class xxx {
public synchronized long requestLease(DatanodeDescriptor dn) {
NodeData node = nodes.get(dn.getDatanodeUuid());
if (node == null) {


log.info("dn requested a lease even though it wasn t yet registered registering now");
}
}

};