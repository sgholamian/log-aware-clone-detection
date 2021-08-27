//,temp,ProducerFlowControlTest.java,263,274,temp,MKahaDBTxRecoveryTest.java,411,419
//,3
public class xxx {
            @Override
            public void doAppend(LoggingEvent event) {
                if (event.getLevel().equals(Level.ERROR) && event.getMessage().toString().startsWith("Corrupt ")) {
                    LOG.info("received expected log message: " + event.getMessage());
                    foundSomeCorruption.set(true);
                } else if (event.getLevel().equals(Level.INFO) && event.getMessage().toString().contains("auto resolving")) {
                    ignoringCorruption.set(true);
                }
            }

};