//,temp,DurableSubProcessWithRestartTest.java,181,231,temp,DurableSubProcessConcurrentCommitActivateNoDuplicateTest.java,209,263
//,3
public class xxx {
        public void send() throws JMSException {
            // do not create new clients now
            // ToDo: Test this case later.
            synchronized (sendMutex) {
                int trans = ++transRover;
                boolean relevantTrans = true; //random(2) > 1;
                ClientType clientType = relevantTrans ? ClientType
                        .randomClientType() : null; // sends this types
                //int count = random(500, 700);
                int count = 1000;

                LOG.info("Sending Trans[id=" + trans + ", count="
                        + count + ", clientType=" + clientType + ", firstID=" + (messageRover+1) + "]");

                Connection con = cf.createConnection();
                Session sess = con
                        .createSession(true, Session.SESSION_TRANSACTED);
                MessageProducer prod = sess.createProducer(null);

                for (int i = 0; i < count; i++) {
                    Message message = sess.createMessage();
                    message.setIntProperty("ID", ++messageRover);
                    message.setIntProperty("TRANS", trans);
                    String type = clientType != null ? clientType
                            .randomMessageType() : ClientType
                            .randomNonRelevantMessageType();
                    message.setStringProperty("TYPE", type);

                    if (CARGO_SIZE > 0)
                        message.setStringProperty("CARGO",
                                getCargo(random(CARGO_SIZE)));

                    prod.send(topic, message);
                    clientManager.onServerMessage(message);
                }

                Message message = sess.createMessage();
                message.setIntProperty("ID", ++messageRover);
                message.setIntProperty("TRANS", trans);
                message.setBooleanProperty("COMMIT", true);
                message.setBooleanProperty("RELEVANT", relevantTrans);
                prod.send(topic, message);
                clientManager.onServerMessage(message);

                committingTransaction = trans;
                sess.commit();
                committingTransaction = -1;

                LOG.info("Committed Trans[id=" + trans + ", count="
                        + count + ", clientType=" + clientType + "], ID=" + messageRover);

                sess.close();
                con.close();
            }
        }

};