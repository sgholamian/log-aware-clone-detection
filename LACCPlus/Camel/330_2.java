//,temp,SpringRestOpenApiReaderModelApiSecurityTest.java,45,88,temp,RestOpenApiReaderModelApiSecurityTest.java,80,122
//,2
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

        assertTrue(json.contains("\"securityDefinitions\" : {"));
        assertTrue(json.contains("\"type\" : \"oauth2\""));
        assertTrue(json.contains("\"authorizationUrl\" : \"http://petstore.swagger.io/oauth/dialog\""));
        assertTrue(json.contains("\"flow\" : \"implicit\""));
        assertTrue(json.contains("\"type\" : \"apiKey\","));
        assertTrue(json.contains("\"in\" : \"header\""));
        assertTrue(json.contains("\"host\" : \"localhost:8080\""));
        assertTrue(json.contains("\"security\" : [ {"));
        assertTrue(json.contains("\"petstore_auth\" : [ \"write:pets\", \"read:pets\" ]"));
        assertTrue(json.contains("\"api_key\" : [ ]"));
        assertTrue(json.contains("\"description\" : \"The user returned\""));
        assertTrue(json.contains("\"$ref\" : \"#/definitions/User\""));
        assertTrue(json.contains("\"x-className\""));
        assertTrue(json.contains("\"format\" : \"org.apache.camel.openapi.User\""));
        assertTrue(json.contains("\"type\" : \"string\""));
        assertTrue(json.contains("\"format\" : \"date\""));
        assertFalse(json.contains("\"enum\""));
        context.stop();
    }

};