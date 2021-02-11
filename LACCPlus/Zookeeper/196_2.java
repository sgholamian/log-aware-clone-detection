//,temp,ReadOnlyZooKeeperServer.java,99,110,temp,LearnerZooKeeperServer.java,148,158
//,3
public class xxx {
    protected void unregisterJMX(Learner peer) {
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