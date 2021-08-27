//,temp,VerifyNetworkConsumersDisconnectTest.java,56,62,temp,AMQ4607Test.java,54,60
//,2
public class xxx {
    private void assertNoUnhandledExceptions() {
        for( Entry<Thread, Throwable> e: unhandledExceptions.entrySet()) {
            LOG.error("Thread:" + e.getKey() + " Had unexpected: " + e.getValue());
        }
        assertTrue("There are no unhandled exceptions, see: log for detail on: " + unhandledExceptions,
                unhandledExceptions.isEmpty());
    }

};