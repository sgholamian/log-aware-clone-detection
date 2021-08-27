//,temp,DurableSubscriberNonPersistentMessageTest.java,158,163,temp,DurableSubscriberNonPersistentMessageTest.java,149,154
//,2
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    Integer pendingQueueSize = (Integer) mbeanServer.getAttribute(new ObjectName(theJmxObject), "PendingQueueSize");
                    LOG.info("pendingQueueSize = " + pendingQueueSize);
                    return pendingQueueSize.intValue() == 0;
                }

};