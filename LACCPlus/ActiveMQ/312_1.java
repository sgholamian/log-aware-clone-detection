//,temp,NIOAsyncSendWithPFCTest.java,128,168,temp,NIOAsyncSendWithPFCTest.java,89,125
//,3
public class xxx {
    public void testAsyncSendPFCExistingConnection() throws Exception {


        BrokerService broker = createBroker();
        broker.waitUntilStarted();

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", TRANSPORT_URL + "?wireFormat.maxInactivityDuration=5000");
        ActiveMQConnection exisitngConnection = (ActiveMQConnection) connectionFactory.createConnection();


        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_PRODUCERS);
        QueueView queueView = getQueueView(broker, DESTINATION_ONE);

        try {

            for (int i = 0; i < NUMBER_OF_PRODUCERS; i++) {

                executorService.submit(new ProducerTask());

            }

            //wait till producer follow control kicks in
            waitForProducerFlowControl(broker, queueView);
            assertTrue("Producer view blocked", getProducerView(broker, DESTINATION_ONE).isProducerBlocked());

            try {
                Session producerSession = exisitngConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            } catch (Exception ex) {
                LOG.error("Ex on create session", ex);
                fail("*** received the following exception when creating producer session:" + ex);
            }


        } finally {
            broker.stop();
            broker.waitUntilStopped();
        }



    }

};