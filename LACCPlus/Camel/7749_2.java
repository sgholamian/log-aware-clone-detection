//,temp,WsProducerConsumerTest.java,119,140,temp,WsProducerConsumerTest.java,96,117
//,2
public class xxx {
    @Test
    public void testTwoRoutesRestartConsumer() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived(TEST_MESSAGE);

        template.sendBody("direct:input", TEST_MESSAGE);

        mock.assertIsSatisfied();

        resetMocks();

        LOG.info("Restarting bar route");
        context.getRouteController().stopRoute("bar");
        Thread.sleep(500);
        context.getRouteController().startRoute("bar");

        mock.expectedBodiesReceived(TEST_MESSAGE);

        template.sendBody("direct:input", TEST_MESSAGE);

        mock.assertIsSatisfied();
    }

};