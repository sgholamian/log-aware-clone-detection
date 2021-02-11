//,temp,sample_5476.java,2,12,temp,sample_4301.java,2,13
//,3
public class xxx {
public synchronized boolean checkLease(DatanodeDescriptor dn, long monotonicNowMs, long id) {
if (id == 0) {
return true;
}
NodeData node = nodes.get(dn.getDatanodeUuid());
if (node == null) {


log.info("br lease is not valid for unknown datanode");
}
}

};