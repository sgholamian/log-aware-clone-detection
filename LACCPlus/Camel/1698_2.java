//,temp,RestOpenApiReaderApiDocsOverrideTest.java,63,90,temp,RestSwaggerReaderPropertyPlaceholderTest.java,85,116
//,3
public class xxx {
    @Test
    public void testReaderRead() throws Exception {
        BeanConfig config = new BeanConfig();
        config.setHost("localhost:8080");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        RestSwaggerReader reader = new RestSwaggerReader();

        RestSwaggerSupport support = new RestSwaggerSupport();
        List<RestDefinition> rests = support.getRestDefinitions(context);

        Swagger swagger = reader.read(rests, null, config, context.getName(), new DefaultClassResolver());
        assertNotNull(swagger);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = mapper.writeValueAsString(swagger);

        LOG.info(json);

        assertTrue(json.contains("\"host\" : \"localhost:8080\""));
        assertTrue(json.contains("\"basePath\" : \"/api\""));
        assertTrue(json.contains("\"/hello/bye\""));
        assertTrue(json.contains("\"summary\" : \"To update the greeting message\""));
        assertTrue(json.contains("\"/hello/bye/{name}\""));
        assertTrue(json.contains("\"/hello/hi/{name}\""));
        assertFalse(json.contains("{foo}"));
        assertFalse(json.contains("{bar}"));

        context.stop();
    }

};