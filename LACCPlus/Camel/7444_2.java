//,temp,RestSwaggerReaderApiDocsOverrideTest.java,63,88,temp,RestOpenApiReaderFileResponseModelTest.java,84,109
//,3
public class xxx {
    @Test
    public void testReaderReadV3() throws Exception {
        BeanConfig config = new BeanConfig();
        config.setHost("localhost:8080");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        Oas30Info info = new Oas30Info();
        config.setInfo(info);
        RestOpenApiReader reader = new RestOpenApiReader();

        OasDocument openApi = reader.read(context, context.getRestDefinitions(), null, config, context.getName(),
                new DefaultClassResolver());
        assertNotNull(openApi);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Object dump = Library.writeNode(openApi);
        String json = mapper.writeValueAsString(dump);

        LOG.info(json);
        assertTrue(json.contains("\"format\" : \"binary\""));
        assertTrue(json.contains("\"type\" : \"string\""));

        context.stop();
    }

};