//,temp,ActiveMQXAConnectionTxInterruptTest.java,134,138,temp,ActiveMQXAConnectionTxInterruptTest.java,106,110
//,2
public class xxx {
            @Override
            public void beforeEnd() throws Exception {
                LOG.info("Interrupting thread: " + Thread.currentThread(), new Throwable("Source"));
                Thread.currentThread().interrupt();
            }

};