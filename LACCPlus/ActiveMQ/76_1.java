//,temp,AMQ6432Test.java,95,101,temp,OfflineDurableSubscriberTimeoutTest.java,123,129
//,3
public class xxx {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getLevel().equals(Level.WARN) && event.getMessage().toString().startsWith("Failed to load next journal")) {
                    LOG.info("received unexpected log message: " + event.getMessage());
                    failed.set(true);
                }
            }

};