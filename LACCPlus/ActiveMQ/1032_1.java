//,temp,SpringBeanTest.java,146,183,temp,SpringBeanTest.java,119,144
//,3
public class xxx {
    @Test
    public void testAddPropertyRefFromFileAndBeanFactory() throws Exception {

        System.setProperty("network.uri", "static:(tcp://localhost:8888)");
        System.setProperty("props.base", "classpath:");
        final String brokerConfig = "SpringPropertyTestFileListBeanFactory-broker";
        applyNewConfig(brokerConfig, "emptyUpdatableConfig1000-spring-property-file-list-and-beanFactory");
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

        assertEquals("our custom prop is applied", "isKing", brokerService.getBrokerName());

        applyNewConfig(brokerConfig, "spring-property-file-list-and-beanFactory-new-nc", SLEEP);

        assertTrue("new network connectors", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return 1 == brokerService.getNetworkConnectors().size();
            }
        }));

        assertEquals("our custom prop is applied", "isKing", brokerService.getNetworkConnectors().get(0).getName());

    }

};