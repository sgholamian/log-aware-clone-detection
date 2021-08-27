//,temp,RestOpenApiReaderPropertyPlaceholderTest.java,84,118,temp,RestOpenApiReaderModelTest.java,108,140
//,3
public class xxx {
    @Test
    public void testReaderRead() throws Exception {
        BeanConfig config = new BeanConfig();
        config.setHost("localhost:8080");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        config.setVersion("2.0");
        RestOpenApiReader reader = new RestOpenApiReader();

        RestOpenApiSupport support = new RestOpenApiSupport();
        List<RestDefinition> rests = support.getRestDefinitions(context);

        OasDocument openApi = reader.read(context, rests, null, config, context.getName(), new DefaultClassResolver());
        assertNotNull(openApi);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Object dump = Library.writeNode(openApi);
        String json = mapper.writeValueAsString(dump);

        log.info(json);

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