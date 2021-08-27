//,temp,NonBlockingConsumerRedeliveryTest.java,155,159,temp,TwoBrokerQueueSendReceiveTest.java,51,55
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("local subs map size = " + bridge.getLocalSubscriptionMap().size());
                return 0 == bridge.getLocalSubscriptionMap().size();
            }

};