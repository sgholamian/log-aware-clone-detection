//,temp,CamelCatalogJsonSchemaTest.java,128,143,temp,CamelCatalogJsonSchemaTest.java,111,126
//,2
public class xxx {
    @Test
    public void testValidateJsonLanguages() throws Exception {
        for (String name : catalog.findLanguageNames()) {
            String json = catalog.languageJSonSchema(name);
            LOG.info("Validating {} language", name);
            LOG.debug("with JSon: {}", json);

            // validate we can parse the json
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(json);
            assertNotNull(tree);

            assertTrue(tree.has("language"), name);
            assertTrue(tree.has("properties"), name);
        }
    }

};