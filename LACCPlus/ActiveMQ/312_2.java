//,temp,NIOAsyncSendWithPFCTest.java,128,168,temp,NIOAsyncSendWithPFCTest.java,89,125
//,3
public class xxx {
    public void testAsyncSendPFCNewConnection() throws Exception {


        BrokerService broker = createBroker();
        broker.waitUntilStarted();


        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_PRODUCERS);
        QueueView queueView = getQueueView(broker, DESTINATION_ONE);

        try {

            for (int i = 0; i < NUMBER_OF_PRODUCERS; i++) {

                executorService.submit(new ProducerTask());

            }

            //wait till producer follow control kicks in
            waitForProducerFlowControl(broker, queueView);


            try {
                sendMessages(1, DESTINATION_TWO, false);
            } catch (Exception ex) {
                LOG.error("Ex on send  new connection", ex);
                fail("*** received the following exception when creating addition producer new connection:" + ex);
            }


        } finally {
            broker.stop();
            broker.waitUntilStopped();
        }


    }

};