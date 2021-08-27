//,temp,StringDataFormatConfigurationAndDocumentationTest.java,39,51,temp,EipDocumentationTest.java,81,93
//,3
public class xxx {
    @Test
    void testFailOverDocumentation() throws Exception {
        try (CamelContext context = new DefaultCamelContext()) {
            String json = context.adapt(CatalogCamelContext.class).getEipParameterJsonSchema("failover");
            LOG.info(json);
            assertNotNull("Should have found json for failover", json);

            assertTrue(json.contains("\"name\": \"failover\""));
            assertTrue(json.contains(
                    "\"exception\": { \"kind\": \"element\", \"displayName\": \"Exception\", \"required\": false, \"type\": \"array\""
                                     + ", \"javaType\": \"java.util.List<java.lang.String>\", \"deprecated\": false"));
        }
    }

};