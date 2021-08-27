//,temp,RestSwaggerReaderModelBookOrderTest.java,65,98,temp,RestSwaggerReaderOverrideHostApiDocsTest.java,37,67
//,3
public class xxx {
    @Test
    public void testReaderRead() throws Exception {
        BeanConfig config = new BeanConfig();
        config.setHost("localhost:8080");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        config.setTitle("Camel User store");
        config.setLicense("Apache 2.0");
        config.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
        RestSwaggerReader reader = new RestSwaggerReader();

        Swagger swagger
                = reader.read(context.getRestDefinitions(), null, config, context.getName(), new DefaultClassResolver());
        assertNotNull(swagger);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = mapper.writeValueAsString(swagger);

        LOG.info(json);

        assertTrue(json.contains("\"host\" : \"localhost:8080\""));
        assertTrue(json.contains("\"description\" : \"The order returned\""));
        assertTrue(json.contains("\"BookOrder\""));
        assertTrue(json.contains("\"LineItem\""));
        assertTrue(json.contains("\"$ref\" : \"#/definitions/BookOrder\""));
        assertTrue(json.contains("\"$ref\" : \"#/definitions/LineItem\""));
        assertTrue(json.contains("\"x-className\""));
        assertTrue(json.contains("\"format\" : \"org.apache.camel.swagger.BookOrder\""));
        assertTrue(json.contains("\"format\" : \"org.apache.camel.swagger.LineItem\""));

        context.stop();
    }

};