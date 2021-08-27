//,temp,ManagedMetricsRoutePolicyTest.java,66,97,temp,ManagedMicrometerRoutePolicyTest.java,40,79
//,3
public class xxx {
    @Test
    public void testMetricsRoutePolicy() throws Exception {
        getMockEndpoint("mock:result").expectedMessageCount(10);

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                template.sendBody("seda:foo", "Hello " + i);
            } else {
                template.sendBody("seda:bar", "Hello " + i);
            }
        }

        assertMockEndpointsSatisfied();

        // there should be 3 names
        assertEquals(3, metricRegistry.getNames().size());

        // there should be 3 mbeans
        Set<ObjectName> set = getMBeanServer().queryNames(new ObjectName("org.apache.camel.metrics:*"), null);
        assertEquals(3, set.size());

        String name = String.format("org.apache.camel:context=%s,type=services,name=MetricsRegistryService",
                context.getManagementName());
        ObjectName on = ObjectName.getInstance(name);
        String json = (String) getMBeanServer().invoke(on, "dumpStatisticsAsJson", null, null);
        assertNotNull(json);
        log.info(json);

        assertTrue(json.contains("test"));
        assertTrue(json.contains("bar.responses"));
        assertTrue(json.contains("foo.responses"));
    }

};