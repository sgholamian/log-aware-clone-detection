//,temp,ReadOnlyZooKeeperServer.java,88,97,temp,FollowerZooKeeperServer.java,162,170
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