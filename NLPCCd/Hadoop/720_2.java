//,temp,sample_5991.java,2,9,temp,sample_5481.java,2,9
//,3
public class xxx {
public synchronized long removeLease(DatanodeDescriptor dn) {
NodeData node = nodes.get(dn.getDatanodeUuid());
if (node == null) {


log.info("can t remove lease for unknown datanode");
}
}

};