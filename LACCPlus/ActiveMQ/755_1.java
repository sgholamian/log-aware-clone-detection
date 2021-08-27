//,temp,JMSClientProducerFlowSendFailIfNoSpace.java,73,88,temp,ProducerFlowControlTest.java,106,116
//,3
public class xxx {
            @Override
            public void run() {
                while (keepGoing.get()) {
                    try {
                        producer.send(session.createTextMessage("Test message"));
                        if (gotResourceException.get()) {
                            // do not flood the broker with requests when full as we are
                            // sending async and they will be limited by the network buffers
                            Thread.sleep(200);
                        }
                    } catch (Exception e) {
                        // with async send, there will be no exceptions
                        LOG.info("Caught excepted exception: {}", e.getMessage());
                    }
                }
            }

};