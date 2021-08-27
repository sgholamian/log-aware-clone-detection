//,temp,MessagePriorityTest.java,808,812,temp,VirtualTopicFlowControlDiscardTest.java,160,165
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                Destination dest  = brokerService.getDestination(new ActiveMQQueue("Consumer.1.VirtualTopic.TEST"));
                LOG.info("Dest 1 size: " + dest.getDestinationStatistics().getEnqueues().getCount());
                return total == dest.getDestinationStatistics().getEnqueues().getCount();
            }

};