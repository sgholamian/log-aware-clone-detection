//,temp,DurableSubProcessConcurrentCommitActivateNoDuplicateTest.java,523,592,temp,DurableSubDelayedUnsubscribeTest.java,516,559
//,3
public class xxx {
        private void process(long processingTime) throws JMSException {
            long end = System.currentTimeMillis() + processingTime;
            long hardEnd = end + 20000; // wait to finish the transaction.
            boolean inTransaction = false;
            int transCount = 0;

            LOG.info(toString() + " ONLINE.");
            Connection con = openConnection();
            Session sess = con.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            MessageConsumer consumer = sess.createDurableSubscriber(topic, SUBSCRIPTION_NAME, selector, false);
            try {
                do {
                    long max = end - System.currentTimeMillis();
                    if (max <= 0) {
                        if (!inTransaction)
                            break;

                        max = hardEnd - System.currentTimeMillis();
                        if (max <= 0)
                            exit("" + this + " failed: Transaction is not finished.");
                    }

                    Message message = consumer.receive(max);
                    if (message == null)
                        continue;

                    if (message.propertyExists("COMMIT")) {
                        message.acknowledge(); // CLIENT_ACKNOWLEDGE

                        LOG.info("Received Trans[id=" + message.getIntProperty("TRANS") + ", count=" + transCount + "] in " + this + ".");

                        inTransaction = false;
                        transCount = 0;
                    } else {
                        inTransaction = true;
                        transCount++;
                    }
                } while (true);
            } finally {
                sess.close();
                con.close();
                LOG.info(toString() + " OFFLINE.");
            }
        }

};