//,temp,ReconnectWithSameClientIDTest.java,56,62,temp,MKahaDBTxRecoveryTest.java,288,294
//,3
public class xxx {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getMessage().toString().startsWith("Failed to register MBean")) {
                    LOG.info("received unexpected log message: " + event.getMessage());
                    failed.set(true);
                }
            }

};