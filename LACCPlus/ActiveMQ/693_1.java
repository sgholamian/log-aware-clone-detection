//,temp,DeadLetterExpiryTest.java,136,140,temp,MBeanTest.java,216,220
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Q " + sharedDlqViewMBean.getName() + " size:" + sharedDlqViewMBean.getQueueSize());
                return sharedDlqViewMBean.getQueueSize() == messageCount;
            }

};