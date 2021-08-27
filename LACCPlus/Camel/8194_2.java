//,temp,ZooKeeperITSupport.java,147,151,temp,ZooKeeperITSupport.java,138,145
//,3
public class xxx {
        public void deleteAll(String node) throws Exception {
            delay(200);
            log.debug("Deleting {} and it's immediate children", node);
            for (String child : zk.getChildren(node, false)) {
                delete(node + "/" + child);
            }
            delete(node);
        }

};