//,temp,JsonUtilsTest.java,54,69,temp,JsonUtilsTest.java,39,52
//,3
public class xxx {
    @Test
    public void getBasicApiJsonSchema() throws Exception {

        // create basic api dto schema
        LOG.info("Basic Api Schema...");
        String basicApiJsonSchema = JsonUtils.getBasicApiJsonSchema();
        LOG.info(basicApiJsonSchema);

        // parse schema to validate
        ObjectMapper objectMapper = JsonUtils.createObjectMapper();
        JsonSchema jsonSchema = objectMapper.readValue(basicApiJsonSchema, JsonSchema.class);
        assertTrue(jsonSchema.isObjectSchema());
        assertFalse(((ObjectSchema) jsonSchema).getOneOf().isEmpty());
    }

};