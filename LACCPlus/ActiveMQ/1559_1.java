//,temp,DurableSubscriptionOfflineTest.java,515,532,temp,QueueDuplicatesTest.java,124,141
//,3
public class xxx {
            @Override
            public void run() {
                try {
                    Connection con = createConnection();
                    final Session sendSession = con.createSession(true, Session.SESSION_TRANSACTED);
                    MessageProducer producer = sendSession.createProducer(topic);
                    for (int i = 0; i < messageCount; i++) {
                        producer.send(sendSession.createTextMessage(payLoad));
                    }
                    LOG.info("About to commit: " + messageCount);
                    sendSession.commit();
                    LOG.info("committed: " + messageCount);
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    exceptions.add(e);
                }
            }

};