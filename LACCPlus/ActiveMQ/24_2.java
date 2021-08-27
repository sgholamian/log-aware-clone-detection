//,temp,PreallocationJournalTest.java,116,120,temp,NonBlockingConsumerRedeliveryTest.java,99,103
//,3
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + received.size() + " messages since rollback.");
                    return received.size() == MSG_COUNT;
                }

};