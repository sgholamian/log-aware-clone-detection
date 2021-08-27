//,temp,ManagedMetricsRoutePolicyTest.java,66,97,temp,ManagedMicrometerRoutePolicyTest.java,40,79
//,3
public class xxx {
    @Test
    public void testMetricsRoutePolicy() throws Exception {
        int count = 10;
        getMockEndpoint("mock:result").expectedMessageCount(count);

        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                template.sendBody("seda:foo", "Hello " + i);
            } else {
                template.sendBody("seda:bar", "Hello " + i);
            }
        }

        assertMockEndpointsSatisfied();

        // there should be 13 names
        List<Meter> meters = meterRegistry.getMeters();
        assertEquals(13, meters.size());

        String name = String.format("org.apache.camel:context=%s,type=services,name=MicrometerRoutePolicyService",
                context.getManagementName());
        ObjectName on = ObjectName.getInstance(name);
        String json = (String) getMBeanServer().invoke(on, "dumpStatisticsAsJson", null, null);
        assertNotNull(json);
        log.info(json);

        assertFalse(json.contains("\"name\" : \"test\""));  // the MicrometerRoutePolicy does NOT display producer metrics
        assertTrue(json.contains("\"routeId\" : \"bar\""));
        assertTrue(json.contains("\"routeId\" : \"foo\""));

        // there should be 2 route policy meter mbeans
        Set<ObjectName> set
                = getMBeanServer().queryNames(new ObjectName("org.apache.camel.micrometer:name=CamelRoutePolicy.*"), null);
        assertEquals(2, set.size());

        String camelContextName = context().getName();
        Long testCount = (Long) getMBeanServer().getAttribute(
                new ObjectName("org.apache.camel.micrometer:name=test.camelContext." + camelContextName), "Count");
        assertEquals(count / 2, testCount.longValue());
    }

};