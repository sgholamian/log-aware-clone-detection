//,temp,VerifyNetworkConsumersDisconnectTest.java,56,62,temp,AMQ4607Test.java,54,60
//,2
public class xxx {
    private void assertNoUnhandeledExceptions() {
        for( Entry<Thread, Throwable> e: unhandeledExceptions.entrySet()) {
            LOG.error("Thread:" + e.getKey() + " Had unexpected: " + e.getValue());
        }
        assertTrue("There are no unhandelled exceptions, see: log for detail on: " + unhandeledExceptions,
                unhandeledExceptions.isEmpty());
    }

};