//,temp,SpringBeanTest.java,146,183,temp,SpringBeanTest.java,119,144
//,3
public class xxx {
    @Test
    public void testAddPropertyRefFromFileAsList() throws Exception {

        System.setProperty("network.uri", "static:(tcp://localhost:8888)");
        System.setProperty("props.base", "classpath:");
        final String brokerConfig = "SpringPropertyTestFileList-broker";
        applyNewConfig(brokerConfig, "emptyUpdatableConfig1000-spring-property-file-list");
        startBroker(brokerConfig);
        assertTrue("broker alive", brokerService.isStarted());

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


    }

};