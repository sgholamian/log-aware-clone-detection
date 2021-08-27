//,temp,AMQ2314Test.java,103,126,temp,DiscriminatingConsumerLoadTest.java,270,299
//,3
public class xxx {
        @Override
        public void run() {
            try {
                Session session = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                final Queue queue = session.createQueue("test");
                MessageConsumer consumer = null;
                if (null != this.jmsSelector) {
                    consumer = session.createConsumer(queue, "JMSType='" + this.jmsSelector + "'");
                } else {
                    consumer = session.createConsumer(queue);
                }

                while (!deliveryHalted && (counterReceived < testSize)) {
                    TextMessage result = (TextMessage) consumer.receive(30000);
                    if (result != null) {
                        counterReceived++;
                        // System.out.println("consuming .... JMSType = " + result.getJMSType() + " received = " +
                        // counterReceived);
                        LOG.info("consuming .... JMSType = " + result.getJMSType() + " received = " + counterReceived);
                    } else {
                        LOG.info("consuming .... timeout while waiting for a message ... broker must have stopped delivery ...  received = " + counterReceived);
                        deliveryHalted = true;
                    }
                }
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

};