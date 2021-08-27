//,temp,ProducerFlowControlTest.java,263,274,temp,MKahaDBTxRecoveryTest.java,411,419
//,3
public class xxx {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getLevel().equals(Level.WARN) && event.getMessage().toString().contains("Usage Manager Memory Limit")) {
                    LOG.info("received warn log message: " + event.getMessage());
                    warnings.incrementAndGet();
                }
                if (event.getLevel().equals(Level.DEBUG) && event.getMessage().toString().contains("Usage Manager Memory Limit")) {
                    LOG.info("received debug log message: " + event.getMessage());
                    debugs.incrementAndGet();
                }

            }

};