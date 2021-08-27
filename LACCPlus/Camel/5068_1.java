//,temp,RestOpenApiReaderModelBookOrderTest.java,102,136,temp,RestSwaggerReaderModelApiSecurityTest.java,80,120
//,3
public class xxx {
    @Test
    public void testReaderReadV3() throws Exception {
        BeanConfig config = new BeanConfig();
        config.setHost("localhost:8080");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        config.setTitle("Camel User store");
        config.setLicense("Apache 2.0");
        config.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
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

        assertTrue(json.contains("\"url\" : \"http://localhost:8080/api\""));
        assertTrue(json.contains("\"description\" : \"The order returned\""));
        assertTrue(json.contains("\"BookOrder\""));
        assertTrue(json.contains("\"LineItem\""));
        assertTrue(json.contains("\"$ref\" : \"#/components/schemas/BookOrder"));
        assertTrue(json.contains("\"$ref\" : \"#/components/schemas/LineItem"));
        assertTrue(json.contains("\"x-className\""));
        assertTrue(json.contains("\"format\" : \"org.apache.camel.openapi.BookOrder\""));
        assertTrue(json.contains("\"format\" : \"org.apache.camel.openapi.LineItem\""));

        context.stop();
    }

};