//,temp,TopicProducerFlowControlTest.java,124,140,temp,TopicProducerToSubFileCursorTest.java,113,128
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                try {
                    if (blockedCounter.get() % 100 == 0) {
                        LOG.info("Got full advisory, usageName: " +
                                message.getStringProperty(AdvisorySupport.MSG_PROPERTY_USAGE_NAME) +
                                ", usageCount: " +
                                message.getLongProperty(AdvisorySupport.MSG_PROPERTY_USAGE_COUNT)
                                + ", blockedCounter: " + blockedCounter.get());
                    }
                    blockedCounter.incrementAndGet();

                } catch (Exception error) {
                    error.printStackTrace();
                    LOG.error("missing advisory property", error);
                }
            }

};