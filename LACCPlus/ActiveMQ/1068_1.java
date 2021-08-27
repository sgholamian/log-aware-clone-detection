//,temp,DurableSubProcessConcurrentCommitActivateNoDuplicateTest.java,523,592,temp,DurableSubDelayedUnsubscribeTest.java,516,559
//,3
public class xxx {
        private void process(long millis) throws JMSException {
            //long end = System.currentTimeMillis() + millis;
            long end = System.currentTimeMillis() + 200;
            long hardEnd = end + 20000; // wait to finish the transaction.
            boolean inTransaction = false;
            int transCount = 0;

            Connection con = openConnection();
            Session sess = con.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            consumer = (ActiveMQMessageConsumer) sess.createDurableSubscriber(topic,
                    SUBSCRIPTION_NAME, selector, false);
            LOG.info(toString() + " ONLINE.");
            try {
                do {
                    long max = end - System.currentTimeMillis();
                    if (max <= 0) {
                        if (!inTransaction) {
                            LOG.info(toString() + " done after no work!");
                            break;
                        }

                        max = hardEnd - System.currentTimeMillis();
                        if (max <= 0)
                            exit("" + this
                                    + " failed: Transaction is not finished.");
                    }

                    Message message = consumer.receive(max);
                    if (message == null)
                        continue;

                    onClientMessage(message);

                    if (message.propertyExists("COMMIT")) {
                        message.acknowledge(); // CLIENT_ACKNOWLEDGE

                        int trans = message.getIntProperty("TRANS");
                        LOG.info("Received Trans[id="
                                + trans + ", count="
                                + transCount + "] in " + this + ".");

                        inTransaction = false;
                        transCount = 0;

                        int committing = server.committingTransaction;
                        if (committing == trans) {
                            LOG.info("Going offline during transaction commit. messageID=" + message.getIntProperty("ID"));
                            break;
                        }
                    } else {
                        inTransaction = true;
                        transCount++;
                        if (1 == transCount) {
                            LOG.info("In Trans[id=" + message.getIntProperty("TRANS") + "] first ID=" + message.getIntProperty("ID"));
                        }
                    }
                } while (true);
            } finally {
                sess.close();
                con.close();

                LOG.info(toString() + " OFFLINE.");

                // Check if the messages are in the waiting
                // list for long time.
                Message topMessage = waitingList.peek();
                if (topMessage != null)
                    checkDeliveryTime(topMessage);
            }
        }

};