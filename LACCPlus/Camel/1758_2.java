//,temp,ThrottlingExceptionRoutePolicyHalfOpenHandlerSedaTest.java,54,100,temp,ThrottlingExceptionRoutePolicyHalfOpenHandlerTest.java,54,99
//,3
public class xxx {
    @Test
    public void testHalfOpenCircuit() throws Exception {
        result.expectedMessageCount(2);
        List<String> bodies = Arrays.asList("Message One", "Message Two");
        result.expectedBodiesReceivedInAnyOrder(bodies);

        result.whenAnyExchangeReceived(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                String msg = exchange.getIn().getBody(String.class);
                exchange.setException(new ThrottlingException(msg));
            }
        });

        // send two messages which will fail
        sendMessage("Message One");
        sendMessage("Message Two");

        final ServiceSupport consumer = (ServiceSupport) context.getRoute("foo").getConsumer();

        // wait long enough to have the consumer suspended
        await().atMost(2, TimeUnit.SECONDS).until(consumer::isSuspended);

        // send more messages
        // but never should get there
        // due to open circuit
        log.debug("sending message three");
        sendMessage("Message Three");

        assertMockEndpointsSatisfied();

        result.reset();
        result.expectedMessageCount(1);
        bodies = Arrays.asList("Message Four");
        result.expectedBodiesReceivedInAnyOrder(bodies);

        // wait long enough to have the consumer resumed
        await().atMost(2, TimeUnit.SECONDS).until(consumer::isStarted);

        // send message
        // should get through
        log.debug("sending message four");
        sendMessage("Message Four");

        assertMockEndpointsSatisfied();
    }

};