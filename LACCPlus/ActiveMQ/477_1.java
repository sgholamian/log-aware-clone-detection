//,temp,FailoverManagedClusterTest.java,130,193,temp,MDBTest.java,315,387
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testFailover() throws Exception {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUri);
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        ActiveMQResourceAdapter adapter = new ActiveMQResourceAdapter();
        adapter.setServerUrl(brokerUri);
        adapter.start(new StubBootstrapContext());

        final CountDownLatch messageDelivered = new CountDownLatch(1);

        final StubMessageEndpoint endpoint = new StubMessageEndpoint() {
            @Override
            public void onMessage(Message message) {
                LOG.info("Received message " + message);
                super.onMessage(message);
                messageDelivered.countDown();
            };
        };

        ActiveMQActivationSpec activationSpec = new ActiveMQActivationSpec();
        activationSpec.setDestinationType(Queue.class.getName());
        activationSpec.setDestination("TEST");
        activationSpec.setResourceAdapter(adapter);
        activationSpec.validate();

        MessageEndpointFactory messageEndpointFactory = new MessageEndpointFactory() {
            @Override
            public MessageEndpoint createEndpoint(XAResource resource) throws UnavailableException {
                endpoint.xaresource = resource;
                return endpoint;
            }

            @Override
            public boolean isDeliveryTransacted(Method method) throws NoSuchMethodException {
                return true;
            }
        };

        // Activate an Endpoint
        adapter.endpointActivation(messageEndpointFactory, activationSpec);

        // Give endpoint a moment to setup and register its listeners
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        MessageProducer producer = session.createProducer(new ActiveMQQueue("TEST"));
        slaveThreadStarted.await(10, TimeUnit.SECONDS);

        // force a failover before send
        LOG.info("Stopping master to force failover..");
        master.stop();
        master = null;
        assertTrue("slave started ok", slave.waitUntilStarted());

        producer.send(session.createTextMessage("Hello, again!"));

        // Wait for the message to be delivered.
        assertTrue(messageDelivered.await(5000, TimeUnit.MILLISECONDS));
    }

};