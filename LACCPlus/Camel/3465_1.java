//,temp,CamelCatalogJsonSchemaTest.java,94,109,temp,CamelCatalogJsonSchemaTest.java,42,60
//,3
public class xxx {
    @Test
    public void testValidateJsonDataFormats() throws Exception {
        for (String name : catalog.findDataFormatNames()) {
            String json = catalog.dataFormatJSonSchema(name);
            LOG.info("Validating {} dataformat", name);
            LOG.debug("with JSon: {}", json);

            // validate we can parse the json
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(json);
            assertNotNull(tree);

            assertTrue(tree.has("dataformat"), name);
            assertTrue(tree.has("properties"), name);
        }
    }

};