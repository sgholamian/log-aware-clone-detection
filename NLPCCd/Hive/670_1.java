//,temp,sample_2717.java,2,13,temp,sample_516.java,2,12
//,3
public class xxx {
public void queryComplete(QueryIdentifier queryIdentifier) {
if (queryIdentifier != null) {
synchronized (knownAppMasters) {
Map<LlapNodeId, AMNodeInfo> amNodeInfoPerQuery = knownAppMasters.remove(queryIdentifier);
if (amNodeInfoPerQuery != null) {


log.info("removed following ams due to query complete");
}
}
}
}

};