//,temp,EipDocumentationTest.java,40,51,temp,SimpleLanguageConfigurationAndDocumentationTest.java,39,52
//,3
public class xxx {
    @Test
    void testLanguageJsonSchema() throws Exception {
        try (CamelContext context = new DefaultCamelContext()) {
            String json = context.adapt(CatalogCamelContext.class).getLanguageParameterJsonSchema("simple");
            assertNotNull(json, "Should have found some auto-generated JSON");
            LOG.info(json);

            assertTrue(json.contains("\"name\": \"simple\""));
            assertTrue(json.contains("\"modelName\": \"simple\""));
            assertTrue(json.contains(
                    "\"resultType\": { \"kind\": \"attribute\", \"displayName\": \"Result Type\", \"required\": false, \"type\": \"string\""
                                     + ", \"javaType\": \"java.lang.String\", \"deprecated\": false"));
        }
    }

};