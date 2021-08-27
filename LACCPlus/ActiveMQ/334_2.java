//,temp,DurableSubSelectorDelayWithRestartTest.java,198,236,temp,DurableSubProcessMultiRestartTest.java,269,292
//,3
public class xxx {
        private void process(long duration) throws JMSException {
            LOG.info(toString() + " ONLINE.");

            Connection con = openConnection();
            Session sess = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer consumer = sess.createDurableSubscriber(topic, SUBSCRIPTION_NAME);

            long end = System.currentTimeMillis() + duration;

            try {
                while (end > System.currentTimeMillis()) {
                    Message message = consumer.receive(100);
                    if (message != null) {
                        LOG.info(toString() + "received message...");
                        msgCount++;
                    }
                }
            } finally {
                sess.close();
                con.close();
                LOG.info(toString() + " OFFLINE.");
            }
        }

};