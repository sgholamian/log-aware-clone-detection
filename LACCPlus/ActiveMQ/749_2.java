//,temp,FailoverConsumerUnconsumedTest.java,299,308,temp,TopicDurableConnectStatsTest.java,129,144
//,3
public class xxx {
    protected ObjectName assertRegisteredObjectName(String name) throws MalformedObjectNameException, NullPointerException {
        ObjectName objectName = new ObjectName(name);

        LOG.info("** Looking for " + name);
        try {
            if (mbeanServer.isRegistered(objectName)) {
                LOG.info("Bean Registered: " + objectName);
            } else {
                LOG.info("Couldn't find Mbean! " + objectName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectName;
    }

};