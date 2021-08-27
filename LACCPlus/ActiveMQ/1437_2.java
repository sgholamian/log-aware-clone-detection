//,temp,JMSConsumerTest.java,146,150,temp,JDBCIOExceptionHandlerMockeryTest.java,52,56
//,3
public class xxx {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LOG.error("unexpected exception {} on thread {}", e, t);
                exceptions.put(t, e);
            }

};