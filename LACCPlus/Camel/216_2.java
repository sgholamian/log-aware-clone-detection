//,temp,StreamedSplitTest.java,46,62,temp,DelimitedTest.java,46,61
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
            Map<?, ?> body = in.getBody(Map.class);
            assertNotNull(body, "Should have found body as a Map but was: " + ObjectHelper.className(in.getBody()));
            assertEquals(expectedItemDesc[counter], body.get("ITEM_DESC"), "ITEM_DESC");
            LOG.info("Result: " + counter + " = " + body);
            counter++;
        }
    }

};