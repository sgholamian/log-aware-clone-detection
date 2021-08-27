//,temp,EipDocumentationTest.java,69,79,temp,GroovyLanguageConfigurationAndDocumentationTest.java,39,49
//,2
public class xxx {
    @Test
    void testLanguageJsonSchema() throws Exception {
        try (CamelContext context = new DefaultCamelContext()) {
            String json = context.adapt(CatalogCamelContext.class).getLanguageParameterJsonSchema("groovy");
            assertNotNull(json, "Should have found some auto-generated JSON");
            LOG.info(json);

            assertTrue(json.contains("\"name\": \"groovy\""));
            assertTrue(json.contains("\"modelName\": \"groovy\""));
        }
    }

};