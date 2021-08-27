//,temp,RestOpenApiReaderApiDocsTest.java,63,93,temp,RestOpenApiReaderEnableVendorExtensionTest.java,111,145
//,3
public class xxx {
    @Test
    public void testEnableVendorExtensionV3() throws Exception {
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

        String camelId = context.getName();
        String routeId = context.getRouteDefinitions().get(0).getId();

        assertTrue(json.contains("\"url\" : \"http://localhost:8080/api\""));
        assertTrue(json.contains("\"description\" : \"The user returned\""));
        assertTrue(json.contains("\"$ref\" : \"#/components/schemas/User\""));
        assertFalse(json.contains("\"enum\""));
        assertTrue(json.contains("\"x-camelContextId\" : \"" + camelId + "\""));
        assertTrue(json.contains("\"x-routeId\" : \"" + routeId + "\""));
        context.stop();
    }

};