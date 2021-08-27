//,temp,RestSwaggerReaderApiDocsTest.java,63,92,temp,RestOpenApiReaderOverrideHostApiDocsTest.java,37,69
//,3
public class xxx {
    @Override
    @Test
    public void testReaderRead() throws Exception {
        BeanConfig config = new BeanConfig();
        config.setHost("localhost:8080");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        config.setVersion("2.0");
        config.setHost("http:mycoolserver:8888/myapi");
        RestOpenApiReader reader = new RestOpenApiReader();

        OasDocument openApi = reader.read(context, context.getRestDefinitions(), null, config, context.getName(),
                new DefaultClassResolver());
        assertNotNull(openApi);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Object dump = Library.writeNode(openApi);
        String json = mapper.writeValueAsString(dump);

        log.info(json);

        assertTrue(json.contains("\"host\" : \"http:mycoolserver:8888/myapi\""));
        assertTrue(json.contains("\"basePath\" : \"/api\""));

        assertFalse(json.contains("\"/hello/bye\""));
        assertFalse(json.contains("\"summary\" : \"To update the greeting message\""));
        assertFalse(json.contains("\"/hello/bye/{name}\""));
        assertTrue(json.contains("\"/hello/hi/{name}\""));

        context.stop();
    }

};