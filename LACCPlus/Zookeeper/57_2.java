//,temp,ReadOnlyZooKeeperServer.java,99,110,temp,LeaderZooKeeperServer.java,201,212
//,2
public class xxx {
    @Override
    protected void unregisterJMX() {
        // unregister from JMX
        try {
            if (jmxDataTreeBean != null) {
                MBeanRegistry.getInstance().unregister(jmxDataTreeBean);
            }
        } catch (Exception e) {
            LOG.warn("Failed to unregister with JMX", e);
        }
        jmxDataTreeBean = null;
    }

};