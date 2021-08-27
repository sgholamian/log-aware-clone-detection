//,temp,DiscountGatewayIT.java,40,47,temp,JaxpTest.java,75,82
//,3
public class xxx {
    @Disabled
    @Test
    public void testAll() throws Exception {
        final List<Discount> result = requestBody("direct://ALL", null, List.class);

        assertNotNull(result, "all result");
        LOG.debug("all: " + result);
    }

};