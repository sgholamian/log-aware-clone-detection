//,temp,ReadOnlyZooKeeperServer.java,88,97,temp,LeaderZooKeeperServer.java,214,224
//,3
public class xxx {
    public void registerJMX(ZooKeeperServerBean serverBean, LocalPeerBean localPeerBean) {
        // register with JMX
        try {
            jmxServerBean = serverBean;
            MBeanRegistry.getInstance().register(serverBean, localPeerBean);
        } catch (Exception e) {
            LOG.warn("Failed to register with JMX", e);
            jmxServerBean = null;
        }
    }

};