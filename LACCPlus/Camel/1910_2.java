//,temp,RestSwaggerReaderEnableVendorExtensionTest.java,75,107,temp,RestSwaggerReaderDayOfWeekTest.java,62,93
//,3
public class xxx {
    @Test
    public void testReaderRead() throws Exception {
        BeanConfig config = new BeanConfig();
        config.setHost("localhost:8080");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        config.setTitle("Day");
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
        assertTrue(json.contains("\"default\" : \"friday\""));
        assertTrue(json.contains("\"enum\" : [ \"monday\", \"tuesday\", \"wednesday\", \"thursday\", \"friday\" ]"));
        assertTrue(json.contains("\"$ref\" : \"#/definitions/DayResponse\""));
        assertTrue(json.contains("\"format\" : \"org.apache.camel.swagger.DayResponse\""));
        assertTrue(json.contains("\"X-Rate-Limit-Limit\" : {"));
        assertTrue(json.contains("\"description\" : \"The number of allowed requests in the current period\""));

        context.stop();
    }

};