//,temp,ReadOnlyZooKeeperServer.java,112,122,temp,LeaderZooKeeperServer.java,201,212
//,3
public class xxx {
    protected void unregisterJMX(ZooKeeperServer zks) {
        // unregister from JMX
        try {
            if (jmxServerBean != null) {
                MBeanRegistry.getInstance().unregister(jmxServerBean);
            }
        } catch (Exception e) {
            LOG.warn("Failed to unregister with JMX", e);
        }
        jmxServerBean = null;
    }

};