//,temp,ConcurrentMoveTest.java,118,126,temp,MBeanOperationTimeoutTest.java,92,100
//,2
public class xxx {
    protected ObjectName assertRegisteredObjectName(String name) throws MalformedObjectNameException, NullPointerException {
        ObjectName objectName = new ObjectName(name);
        if (mbeanServer.isRegistered(objectName)) {
            LOG.info("Bean Registered: " + objectName);
        } else {
            fail("Could not find MBean!: " + objectName);
        }
        return objectName;
    }

};