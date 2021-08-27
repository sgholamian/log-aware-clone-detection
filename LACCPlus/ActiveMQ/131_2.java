//,temp,ReconnectWithSameClientIDTest.java,56,62,temp,MKahaDBTxRecoveryTest.java,288,294
//,3
public class xxx {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getLevel().equals(Level.ERROR) && event.getMessage().toString().startsWith("Corrupt ")) {
                    LOG.info("received expected log message: " + event.getMessage());
                    foundSomeCorruption.set(true);
                }
            }

};