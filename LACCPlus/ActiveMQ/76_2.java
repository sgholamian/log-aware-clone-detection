//,temp,AMQ6432Test.java,95,101,temp,OfflineDurableSubscriberTimeoutTest.java,123,129
//,3
public class xxx {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getLevel().isGreaterOrEqual(Level.WARN)) {
                    LOG.info("received unexpected log message: " + event.getMessage());
                    foundLogMessage.set(true);
                }
            }

};