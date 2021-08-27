//,temp,DurableSubProcessWithRestartTest.java,474,530,temp,DurableSubSelectorDelayTest.java,186,220
//,3
public class xxx {
        private void process() throws JMSException {
            long end = System.currentTimeMillis() + 20000;
            int transCount = 0;

            LOG.info(toString() + " ONLINE.");
            Connection con = openConnection();

            Session sess = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = sess.createDurableSubscriber(topic, subName, selector, false);

            try {

                do {
                    long max = end - System.currentTimeMillis();

                    if (max <= 0) {
                        break;
                    }

                    Message message = consumer.receive(max);
                    if (message == null) {
                        continue;
                    }

                    LOG.info("Received Trans[id=" + message.getIntProperty("TRANS") + ", count=" + transCount + "] in " + this + ".");

                } while (true);

            } finally {
                sess.close();
                con.close();

                LOG.info(toString() + " OFFLINE.");
            }
        }

};