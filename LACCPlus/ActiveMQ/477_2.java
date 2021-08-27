//,temp,FailoverManagedClusterTest.java,130,193,temp,MDBTest.java,315,387
//,3
public class xxx {
    @Test
    public void testParallelMessageDelivery() throws Exception {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        ActiveMQResourceAdapter adapter = new ActiveMQResourceAdapter();
        adapter.setServerUrl("vm://localhost?broker.persistent=false&create=false");
        adapter.start(new StubBootstrapContext());

        final CountDownLatch messageDelivered = new CountDownLatch(10);

        final StubMessageEndpoint endpoint = new StubMessageEndpoint() {


            @Override
            public void beforeDelivery(Method method) throws NoSuchMethodException, ResourceException {
            }

            @Override
            public void afterDelivery() throws ResourceException {
            }

            public void onMessage(Message message) {
                LOG.info("Message:" + message);
                super.onMessage(message);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                messageDelivered.countDown();
            };
        };

        ActiveMQActivationSpec activationSpec = new ActiveMQActivationSpec();
        activationSpec.setDestinationType(Queue.class.getName());
        activationSpec.setDestination("TEST");
        activationSpec.setResourceAdapter(adapter);
        activationSpec.validate();

        MessageEndpointFactory messageEndpointFactory = new MessageEndpointFactory() {
            public MessageEndpoint createEndpoint(XAResource resource) throws UnavailableException {
                endpoint.xaresource = resource;
                return endpoint;
            }

            public boolean isDeliveryTransacted(Method method) throws NoSuchMethodException {
                return false;
            }
        };

        // Activate an Endpoint
        adapter.endpointActivation(messageEndpointFactory, activationSpec);


        // Send the broker a message to that endpoint
        MessageProducer producer = session.createProducer(new ActiveMQQueue("TEST"));
        for (int i=0;i<10;i++) {
            producer.send(session.createTextMessage(i+"-Hello!"));
        }

        connection.close();

        // Wait for the message to be delivered.
        assertTrue(messageDelivered.await(5000, TimeUnit.MILLISECONDS));

        // Shut the Endpoint down.
        adapter.endpointDeactivation(messageEndpointFactory, activationSpec);
        adapter.stop();

    }

};