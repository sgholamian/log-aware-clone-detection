//,temp,DurableSubscriptionOfflineTest.java,515,532,temp,QueueDuplicatesTest.java,124,141
//,3
public class xxx {
        public void run() {
            try {
                Session session = createSession(brokerConnection);
                Destination dest = session.createQueue(subject);
                MessageProducer producer = session.createProducer(dest);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                for (int i = 0; i < 20; i++) {
                    String txt = "Text Message: " + i;
                    TextMessage msg = session.createTextMessage(txt);
                    producer.send(msg);
                    LOG.info(formatter.format(new Date()) + " Sent ==> " + msg + " to " + subject);
                    Thread.sleep(1000);
                }
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

};