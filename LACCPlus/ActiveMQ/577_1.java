//,temp,TopicProducerFlowControlTest.java,240,246,temp,TopicProducerFlowControlTest.java,145,151
//,2
public class xxx {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getLevel().equals(Level.WARN) && event.getMessage().toString().contains("Usage Manager memory limit reached")) {
                    LOG.info("received  log message: " + event.getMessage());
                    warnings.incrementAndGet();
                }
            }

};