//,temp,ProxyProtocolTest.java,192,208,temp,BaseNettyTest.java,53,68
//,3
public class xxx {
    @AfterAll
    public static void verifyNoLeaks() throws Exception {
        // Force GC to bring up leaks
        System.gc();
        // Kick leak detection logging
        ByteBufAllocator.DEFAULT.buffer(1).release();
        Collection<LogEvent> events = LogCaptureAppender.getEvents(ResourceLeakDetector.class);
        if (!events.isEmpty()) {
            String message = "Leaks detected while running tests: " + events;
            // Just write the message into log to help debug
            for (LogEvent event : events) {
                LOG.info(event.getMessage().toString());
            }
            throw new AssertionError(message);
        }
    }

};