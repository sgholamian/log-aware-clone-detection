//,temp,CamelCatalogJsonSchemaTest.java,94,109,temp,CamelCatalogJsonSchemaTest.java,42,60
//,3
public class xxx {
    @Test
    public void testValidateJsonComponent() throws Exception {
        for (String name : catalog.findComponentNames()) {
            String json = catalog.componentJSonSchema(name);
            LOG.info("Validating {} component", name);
            LOG.debug("with JSon: {}", json);

            // validate we can parse the json
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(json);
            assertNotNull(tree);

            assertTrue(tree.has("component"), name);
            assertTrue(tree.has("componentProperties"), name);
            assertTrue(tree.has("properties"), name);

            validateComponentSyntax(name, tree);
        }
    }

};