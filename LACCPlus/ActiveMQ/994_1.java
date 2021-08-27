//,temp,DurableSubscriberWithNetworkRestartTest.java,123,159,temp,DuplexNetworkMBeanTest.java,216,248
//,3
public class xxx {
    private int countMbeans(BrokerService broker, String type, int timeout) throws Exception {
        final long expiryTime = System.currentTimeMillis() + timeout;

        if (!type.contains("=")) {
            type = type + "=*";
        }

        final ObjectName beanName = new ObjectName("org.apache.activemq:type=Broker,brokerName="
                + broker.getBrokerName() + "," + type +",*");
        Set<ObjectName> mbeans = null;
        int count = 0;
        do {
            if (timeout > 0) {
                Thread.sleep(100);
            }

            mbeans = broker.getManagementContext().queryNames(beanName, null);
            if (mbeans != null) {
                count = mbeans.size();
                LOG.info("Found: " + count + ", matching type: " +type);
                for (ObjectName objectName : mbeans) {
                    LOG.info("" + objectName);
                }
                //} else {
                //logAllMbeans(broker);
            }
        } while ((mbeans == null || mbeans.isEmpty()) && expiryTime > System.currentTimeMillis());

        // If port 1099 is in use when the Broker starts, starting the jmx connector
        // will fail.  So, if we have no mbsc to query, skip the test.
        if (timeout > 0) {
            assumeNotNull(mbeans);
        }

        return count;

    }

};