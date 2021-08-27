//,temp,SpringBeanTest.java,34,67,temp,NameSpaceXmlLoadTest.java,34,57
//,3
public class xxx {
    @Test
    public void testCanLoad() throws Exception {
        final String brokerConfig =  "namespace-prefix";
        System.setProperty("data", IOHelper.getDefaultDataDirectory());
        System.setProperty("broker-name", brokerConfig);
        startBroker(brokerConfig);
        assertTrue("broker alive", brokerService.isStarted());
        assertEquals("nameMatch", brokerConfig, brokerService.getBrokerName());

        // verify runtimeConfig active
        ObjectName objectName =
                new ObjectName(brokerService.getBrokerObjectName().toString() +
                        RuntimeConfigurationBroker.objectNamePropsAppendage);
        RuntimeConfigurationViewMBean runtimeConfigurationView =
                (RuntimeConfigurationViewMBean) brokerService.getManagementContext().newProxyInstance(objectName,
                        RuntimeConfigurationViewMBean.class, false);

        HashMap<String, String> props = new HashMap<String, String>();
        IntrospectionSupport.getProperties(runtimeConfigurationView, props, null);
        LOG.info("mbean attributes before: " + props);
        String propOfInterest = "modified";
        assertNotEquals("modified is valid", "unknown", props.get(propOfInterest));

    }

};