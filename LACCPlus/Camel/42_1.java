//,temp,EipDocumentationTest.java,69,79,temp,GroovyLanguageConfigurationAndDocumentationTest.java,39,49
//,2
public class xxx {
    @Test
    void testSimpleDocumentation() throws Exception {
        try (CamelContext context = new DefaultCamelContext()) {
            String json = context.adapt(CatalogCamelContext.class).getEipParameterJsonSchema("simple");
            LOG.info(json);
            assertNotNull("Should have found json for simple", json);

            assertTrue(json.contains("\"label\": \"language,core,java\""));
            assertTrue(json.contains("\"name\": \"simple\""));
        }
    }

};