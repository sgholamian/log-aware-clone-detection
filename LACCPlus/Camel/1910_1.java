//,temp,RestSwaggerReaderEnableVendorExtensionTest.java,75,107,temp,RestSwaggerReaderDayOfWeekTest.java,62,93
//,3
public class xxx {
    @Test
    public void testEnableVendorExtension() throws Exception {
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

        String camelId = context.getName();
        String routeId = context.getRouteDefinitions().get(0).getId();

        assertTrue(json.contains("\"host\" : \"localhost:8080\""));
        assertTrue(json.contains("\"description\" : \"The user returned\""));
        assertTrue(json.contains("\"$ref\" : \"#/definitions/User\""));
        assertFalse(json.contains("\"enum\""));
        assertTrue(json.contains("\"x-camelContextId\" : \"" + camelId + "\""));
        assertTrue(json.contains("\"x-routeId\" : \"" + routeId + "\""));
        context.stop();
    }

};