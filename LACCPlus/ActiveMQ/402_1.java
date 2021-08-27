//,temp,SpringBeanTest.java,34,67,temp,NameSpaceXmlLoadTest.java,34,57
//,3
public class xxx {
    @Test
    public void testModifiable() throws Exception {
        final String brokerConfig =  "SpringBeanTest-broker";
        applyNewConfig(brokerConfig, "emptyUpdatableConfig1000-spring-bean");
        startBroker(brokerConfig);
        assertTrue("broker alive", brokerService.isStarted());

        // apply via jmx
        ObjectName objectName =
                new ObjectName(brokerService.getBrokerObjectName().toString() +
                        RuntimeConfigurationBroker.objectNamePropsAppendage);
        RuntimeConfigurationViewMBean runtimeConfigurationView =
                (RuntimeConfigurationViewMBean) brokerService.getManagementContext().newProxyInstance(objectName,
                        RuntimeConfigurationViewMBean.class, false);

        String propOfInterest = "modified";
        HashMap<String, String> props = new HashMap<String, String>();
        IntrospectionSupport.getProperties(runtimeConfigurationView, props, null);
        LOG.info("mbean attributes before: " + props);

        assertNotEquals("unknown", props.get(propOfInterest));

        String result = runtimeConfigurationView.updateNow();

        LOG.info("Result from update: " + result);

        assertTrue("got sensible result", result.contains("No material change"));

        HashMap<String, String> propsAfter = new HashMap<String, String>();
        IntrospectionSupport.getProperties(runtimeConfigurationView, propsAfter, null);
        LOG.info("mbean attributes after: " + propsAfter);

        assertEquals("modified is same", props.get(propOfInterest), propsAfter.get(propOfInterest));
    }

};