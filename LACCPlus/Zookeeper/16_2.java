//,temp,ReadOnlyZooKeeperServer.java,88,97,temp,LeaderZooKeeperServer.java,214,224
//,3
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