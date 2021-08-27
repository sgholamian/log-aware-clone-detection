//,temp,TopicBridgeSelectorConduitOnOff.java,116,127,temp,AMQ4351Test.java,94,104
//,3
public class xxx {
        private void sendMessage() {
            try {
                producer.send(session.createTextMessage("Test"));
                long i = size.incrementAndGet();
                if( (i % 1000) == 0 ) {
                    LOG.info("produced " + i + ".");
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

};