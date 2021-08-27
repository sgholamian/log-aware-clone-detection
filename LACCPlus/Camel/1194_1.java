//,temp,StringDataFormatConfigurationAndDocumentationTest.java,39,51,temp,EipDocumentationTest.java,81,93
//,3
public class xxx {
    @Test
    void testDataFormatJsonSchema() throws Exception {
        try (CamelContext context = new DefaultCamelContext()) {
            String json = context.adapt(CatalogCamelContext.class).getDataFormatParameterJsonSchema("string");
            assertNotNull(json, "Should have found some auto-generated JSON");
            LOG.info(json);

            assertTrue(json.contains("\"name\": \"string\""));
            assertTrue(json.contains("\"modelName\": \"string\""));
            assertTrue(json.contains(
                    "\"charset\": { \"kind\": \"attribute\", \"displayName\": \"Charset\", \"required\": false, \"type\": \"string\""));
        }
    }

};