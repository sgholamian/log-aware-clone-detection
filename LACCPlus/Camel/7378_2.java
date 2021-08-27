//,temp,ContextErrorHandlerTest.java,69,93,temp,RouteBuilderTest.java,303,323
//,3
public class xxx {
    @Test
    public void testComplexExpressions() throws Exception {
        // START SNIPPET: e7
        RouteBuilder builder = new RouteBuilder() {
            public void configure() {
                errorHandler(deadLetterChannel("mock:error"));

                from("direct:a").filter(header("foo").isEqualTo(123)).to("direct:b");
            }
        };
        // END SNIPPET: e7

        List<Route> routes = getRouteList(builder);
        log.debug("Created routes: " + routes);

        assertEquals(1, routes.size(), "Number routes created");
        for (Route route : routes) {
            Endpoint key = route.getEndpoint();
            assertEquals("direct://a", key.getEndpointUri(), "From endpoint");
        }
    }

};