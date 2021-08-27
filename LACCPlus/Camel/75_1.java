//,temp,PlanGatewayIT.java,38,45,temp,AddOnGatewayIT.java,38,45
//,2
public class xxx {
    @Disabled
    @Test
    public void testAll() throws Exception {
        final List<Plan> result = requestBody("direct://ALL", null, List.class);

        assertNotNull(result, "all result");
        LOG.debug("all: " + result);
    }

};