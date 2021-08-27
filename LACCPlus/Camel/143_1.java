//,temp,CamelCatalogJsonSchemaTest.java,128,143,temp,CamelCatalogJsonSchemaTest.java,111,126
//,2
public class xxx {
    @Test
    public void testValidateJsonModels() throws Exception {
        for (String name : catalog.findModelNames()) {
            String json = catalog.modelJSonSchema(name);
            LOG.info("Validating {} model", name);
            LOG.debug("with JSon: {}", json);

            // validate we can parse the json
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(json);
            assertNotNull(tree);

            assertTrue(tree.has("model"), name);
            assertTrue(tree.has("properties"), name);
        }
    }

};