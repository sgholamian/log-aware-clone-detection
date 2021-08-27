//,temp,VertxRoutePubSubTest.java,36,50,temp,VertxRouteTest.java,36,50
//,2
public class xxx {
    @Test
    public void testVertxMessages() throws Exception {
        resultEndpoint = context.getEndpoint(resultUri, MockEndpoint.class);
        resultEndpoint.expectedBodiesReceivedInAnyOrder(body1, body2);

        template.sendBody(startUri, body1);
        template.sendBody(startUri, body2);

        resultEndpoint.assertIsSatisfied();

        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        for (Exchange exchange : list) {
            log.info("Received exchange: " + exchange + " headers: " + exchange.getIn().getHeaders());
        }
    }

};