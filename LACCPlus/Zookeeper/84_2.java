//,temp,ReadOnlyZooKeeperServer.java,88,97,temp,LeaderZooKeeperServer.java,191,199
//,3
public class xxx {
    boolean registerJMX(LearnerHandlerBean handlerBean) {
        try {
            MBeanRegistry.getInstance().register(handlerBean, jmxServerBean);
            return true;
        } catch (JMException e) {
            LOG.warn("Could not register connection", e);
        }
        return false;
    }

};