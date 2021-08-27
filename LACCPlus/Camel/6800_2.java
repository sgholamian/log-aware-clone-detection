//,temp,CsvRouteTest.java,105,136,temp,CsvRouteTest.java,67,103
//,3
public class xxx {
    @Test
    void testMultipleMessages() throws Exception {
        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:resultMulti",
                MockEndpoint.class);
        resultEndpoint.expectedMessageCount(2);
        Map<String, Object> body1 = new LinkedHashMap<>();
        body1.put("foo", "abc");
        body1.put("bar", 123);

        Map<String, Object> body2 = new LinkedHashMap<>();
        body2.put("foo", "def");
        body2.put("bar", 456);
        body2.put("baz", 789);

        template.sendBody("direct:startMulti", body1);
        template.sendBody("direct:startMulti", body2);

        resultEndpoint.assertIsSatisfied();
        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        Message in1 = list.get(0).getIn();
        String text1 = in1.getBody(String.class);

        LOG.debug("Received " + text1);
        assertTrue(Pattern.matches("(abc,123)|(123,abc)", text1.trim()), "First CSV body has wrong value");

        Message in2 = list.get(1).getIn();
        String text2 = in2.getBody(String.class);

        LOG.debug("Received " + text2);

        // fields should keep the same order from one call to the other
        if (text1.trim().equals("abc,123")) {
            assertEquals("def,456,789", text2.trim(), "Second CSV body has wrong value");
        } else {
            assertEquals("456,def,789", text2.trim(), "Second CSV body has wrong value");
        }
    }

};