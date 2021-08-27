//,temp,MessagePriorityTest.java,778,782,temp,MessagePriorityTest.java,601,605
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Enqueues: " + destinationStatistics.getEnqueues().getCount() + ", Dequeues: " + destinationStatistics.getDequeues().getCount());
                return destinationStatistics.getEnqueues().getCount() == backlog + 10 && destinationStatistics.getDequeues().getCount() == 500;
            }

};