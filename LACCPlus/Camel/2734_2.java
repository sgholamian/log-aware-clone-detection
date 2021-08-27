//,temp,ZooKeeperITSupport.java,104,107,temp,ZooKeeperITSupport.java,99,102
//,2
public class xxx {
        public void create(String node, String data) throws Exception {
            log.debug(String.format("Creating node '%s' with data '%s' ", node, data));
            create(node, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }

};