//,temp,DurableSubscriberWithNetworkRestartTest.java,161,173,temp,DuplexNetworkMBeanTest.java,250,262
//,3
public class xxx {
    private void logAllMbeans(BrokerService broker) throws MalformedURLException {
        try {
            // trace all existing MBeans
            Set<?> all = mBeanServer.queryNames(null, null);
            LOG.info("Total MBean count=" + all.size());
            for (Object o : all) {
                ObjectInstance bean = (ObjectInstance)o;
                LOG.info(bean.getObjectName().toString());
            }
        } catch (Exception ignored) {
            LOG.warn("getMBeanServer ex: " + ignored);
        }
    }

};