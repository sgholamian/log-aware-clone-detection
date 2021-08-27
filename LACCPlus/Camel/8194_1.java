//,temp,ZooKeeperITSupport.java,147,151,temp,ZooKeeperITSupport.java,138,145
//,3
public class xxx {
        public void delete(String node) throws Exception {
            delay(200);
            log.debug("Deleting node " + node);
            zk.delete(node, -1);
        }

};