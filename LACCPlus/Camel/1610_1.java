//,temp,JsonUtilsTest.java,54,69,temp,JsonUtilsTest.java,39,52
//,3
public class xxx {
    @Test
    public void getSObjectJsonSchema() throws Exception {

        // create sobject dto schema
        SObjectDescription description = new Account().description();

        LOG.info("SObject Schema...");
        String sObjectJsonSchema = JsonUtils.getSObjectJsonSchema(description);
        LOG.info(sObjectJsonSchema);

        // parse schema to validate
        ObjectMapper objectMapper = JsonUtils.createObjectMapper();
        JsonSchema jsonSchema = objectMapper.readValue(sObjectJsonSchema, JsonSchema.class);
        assertTrue(jsonSchema.isObjectSchema());
        assertEquals(2, ((ObjectSchema) jsonSchema).getOneOf().size());
    }

};