//,temp,MessagePriorityTest.java,808,812,temp,VirtualTopicFlowControlDiscardTest.java,160,165
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Enqueues: " + destinationStatistics.getEnqueues().getCount() + ", Dequeues: " + destinationStatistics.getDequeues().getCount());
                return destinationStatistics.getEnqueues().getCount() == numMessages && destinationStatistics.getDequeues().getCount() == numMessages;
            }

};