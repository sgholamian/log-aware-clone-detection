//,temp,ReadOnlyZooKeeperServer.java,112,122,temp,LearnerZooKeeperServer.java,148,158
//,2
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