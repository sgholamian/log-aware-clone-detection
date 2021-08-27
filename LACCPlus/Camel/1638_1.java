//,temp,RestOpenApiReaderDayOfWeekTest.java,62,96,temp,RestOpenApiReaderTest.java,66,104
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
        assertTrue(json.contains("\"default\" : \"friday\""));
        assertTrue(json.contains("\"enum\" : [ \"monday\", \"tuesday\", \"wednesday\", \"thursday\", \"friday\" ]"));
        assertTrue(json.contains("\"$ref\" : \"#/definitions/DayResponse\""));
        assertTrue(json.contains("\"format\" : \"org.apache.camel.openapi.DayResponse\""));
        assertTrue(json.contains("\"X-Rate-Limit-Limit\" : {"));
        assertTrue(json.contains("\"description\" : \"The number of allowed requests in the current period\""));

        context.stop();

    }

};