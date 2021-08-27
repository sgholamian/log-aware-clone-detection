//,temp,RestOpenApiReaderDayOfWeekTest.java,62,96,temp,RestOpenApiReaderTest.java,66,104
//,3
public class xxx {
    @Test
    public void testReaderRead() throws Exception {
        BeanConfig config = new BeanConfig();
        config.setHost("localhost:8080");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        Oas20Info info = new Oas20Info();
        config.setInfo(info);
        config.setVersion("2.0");
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

        assertTrue(json.contains("\"host\" : \"localhost:8080\""));
        assertTrue(json.contains("\"basePath\" : \"/api\""));
        assertTrue(json.contains("\"/hello/bye\""));
        assertTrue(json.contains("\"summary\" : \"To update the greeting message\""));
        assertTrue(json.contains("\"/hello/bye/{name}\""));
        assertTrue(json.contains("\"/hello/hi/{name}\""));
        assertTrue(json.contains("\"type\" : \"number\""));
        assertTrue(json.contains("\"format\" : \"float\""));
        assertTrue(json.contains("\"application/xml\" : \"<hello>Hi</hello>\""));
        assertTrue(json.contains("\"x-example\" : \"Donald Duck\""));
        assertTrue(json.contains("\"success\" : \"123\""));
        assertTrue(json.contains("\"error\" : \"-1\""));
        assertTrue(json.contains("\"type\" : \"string\""));

        context.stop();
    }

};