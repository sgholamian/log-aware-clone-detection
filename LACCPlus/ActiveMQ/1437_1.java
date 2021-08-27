//,temp,JMSConsumerTest.java,146,150,temp,JDBCIOExceptionHandlerMockeryTest.java,52,56
//,3
public class xxx {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LOG.error("Uncaught exception:", e);
                exceptions.put(t, e);
            }

};