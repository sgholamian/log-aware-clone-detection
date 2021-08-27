//,temp,TwoBrokerTempQueueAdvisoryTest.java,102,106,temp,TwoBrokerTempQueueAdvisoryTest.java,93,97
//,2
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("BrokerA temp advisory enque count: " + brokerAView.getEnqueueCount());
                return iterations * 2 == brokerAView.getEnqueueCount();
            }

};