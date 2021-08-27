//,temp,EipDocumentationTest.java,40,51,temp,SimpleLanguageConfigurationAndDocumentationTest.java,39,52
//,3
public class xxx {
    @Test
    void testDocumentation() throws Exception {
        try (CamelContext context = new DefaultCamelContext()) {
            String json = context.adapt(CatalogCamelContext.class).getEipParameterJsonSchema("from");
            LOG.info(json);
            assertNotNull(json, "Should have found json for from");

            assertTrue(json.contains("\"name\": \"from\""));
            assertTrue(json.contains("\"uri\": { \"kind\": \"attribute\""));
            assertTrue(json.contains("\"ref\": { \"kind\": \"attribute\""));
        }
    }

};