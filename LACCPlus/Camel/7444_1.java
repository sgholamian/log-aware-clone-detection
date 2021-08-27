//,temp,RestSwaggerReaderApiDocsOverrideTest.java,63,88,temp,RestOpenApiReaderFileResponseModelTest.java,84,109
//,3
public class xxx {
    @Test
    public void testReaderRead() throws Exception {
        BeanConfig config = new BeanConfig();
        config.setHost("localhost:8080");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        RestSwaggerReader reader = new RestSwaggerReader();

        Swagger swagger
                = reader.read(context.getRestDefinitions(), null, config, context.getName(), new DefaultClassResolver());
        assertNotNull(swagger);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = mapper.writeValueAsString(swagger);

        LOG.info(json);

        assertFalse(json.contains("\"/hello/bye\""));
        assertFalse(json.contains("\"summary\" : \"To update the greeting message\""));
        assertTrue(json.contains("\"/hello/bye/{name}\""));
        assertFalse(json.contains("\"/hello/hi/{name}\""));

        context.stop();
    }

};