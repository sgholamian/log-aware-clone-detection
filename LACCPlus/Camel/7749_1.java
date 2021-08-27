//,temp,WsProducerConsumerTest.java,119,140,temp,WsProducerConsumerTest.java,96,117
//,2
public class xxx {
    @Test
    public void testTwoRoutesRestartProducer() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived(TEST_MESSAGE);

        template.sendBody("direct:input", TEST_MESSAGE);

        mock.assertIsSatisfied();

        resetMocks();

        LOG.info("Restarting foo route");
        context.getRouteController().stopRoute("foo");
        Thread.sleep(500);
        context.getRouteController().startRoute("foo");

        mock.expectedBodiesReceived(TEST_MESSAGE);

        template.sendBody("direct:input", TEST_MESSAGE);

        mock.assertIsSatisfied();
    }

};