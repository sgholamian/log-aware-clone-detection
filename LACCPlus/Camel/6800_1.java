//,temp,CsvRouteTest.java,105,136,temp,CsvRouteTest.java,67,103
//,3
public class xxx {
    @Test
    void testPresetConfig() throws Exception {
        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:resultMultiCustom",
                MockEndpoint.class);
        resultEndpoint.expectedMessageCount(2);
        Map<String, Object> body1 = new LinkedHashMap<>();
        body1.put("foo", "abc");
        body1.put("bar", 123);

        Map<String, Object> body2 = new LinkedHashMap<>();
        body2.put("foo", "def");
        body2.put("bar", 456);
        body2.put("baz", 789);
        body2.put("buz", "000");

        template.sendBody("direct:startMultiCustom", body1);
        template.sendBody("direct:startMultiCustom", body2);

        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        Message in1 = list.get(0).getIn();
        String text1 = in1.getBody(String.class);

        LOG.debug("Received " + text1);
        assertEquals("abc;;123", text1.trim(), "First CSV body has wrong value");

        Message in2 = list.get(1).getIn();
        String text2 = in2.getBody(String.class);

        LOG.debug("Received " + text2);
        assertEquals("def;789;456", text2.trim(), "Second CSV body has wrong value");

    }

};