//,temp,TwoBrokerTempQueueAdvisoryTest.java,102,106,temp,TwoBrokerTempQueueAdvisoryTest.java,93,97
//,2
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("BrokerB temp advisory enque count: " + brokerBView.getEnqueueCount());
                return iterations * 2 == brokerBView.getEnqueueCount();
            }

};