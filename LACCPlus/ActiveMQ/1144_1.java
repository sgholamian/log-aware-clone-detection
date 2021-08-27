//,temp,DurableSubscriberNonPersistentMessageTest.java,158,163,temp,DurableSubscriberNonPersistentMessageTest.java,149,154
//,2
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    Long cursorMemoryUsage = (Long) mbeanServer.getAttribute(new ObjectName(theJmxObject), "CursorMemoryUsage");
                    LOG.info("cursorMemoryUsage = " + cursorMemoryUsage);
                    return cursorMemoryUsage.longValue() == 0L;
                }

};