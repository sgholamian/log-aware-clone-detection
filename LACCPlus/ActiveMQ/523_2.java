//,temp,AMQ4485LowLimitTest.java,427,431,temp,AdvisoryViaNetworkTest.java,375,379
//,2
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("verify infos " + broker.getBrokerName() + ", len: " + regionBroker.getPeerBrokerInfos().length);
                return max == regionBroker.getPeerBrokerInfos().length;
            }

};