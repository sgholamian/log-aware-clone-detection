//,temp,ReadOnlyZooKeeperServer.java,112,122,temp,LeaderZooKeeperServer.java,214,224
//,2
public class xxx {
    protected void unregisterJMX(Leader leader) {
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