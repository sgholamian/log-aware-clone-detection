//,temp,DestinationGCStressTest.java,164,175,temp,DestinationGCStressTest.java,98,104
//,3
public class xxx {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getLevel().equals(Level.ERROR) && event.getMessage().toString().startsWith("Failed to remove inactive")) {
                    logger.info("received unexpected log message: " + event.getMessage());
                    failed.set(true);
                }
            }

};