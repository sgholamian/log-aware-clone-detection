//,temp,FixedLengthAllowShortTest.java,52,68,temp,FixedLengthTest.java,46,62
//,2
public class xxx {
    @Test
    public void testCamel() throws Exception {
        results.expectedMessageCount(4);
        results.assertIsSatisfied();

        int counter = 0;
        List<Exchange> list = results.getReceivedExchanges();
        for (Exchange exchange : list) {
            Message in = exchange.getIn();
            assertEquals(in.getHeader("camelFlatpackCounter"), counter, "counter");
            Map<?, ?> body = in.getBody(Map.class);
            assertNotNull(body, "Should have found body as a Map but was: " + ObjectHelper.className(in.getBody()));
            assertEquals(expectedFirstName[counter], body.get("FIRSTNAME"), "FIRSTNAME");
            LOG.info("Result: " + counter + " = " + body);
            counter++;
        }
    }

};