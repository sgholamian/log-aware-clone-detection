//,temp,ReadOnlyZooKeeperServer.java,112,122,temp,ReadOnlyZooKeeperServer.java,99,110
//,3
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