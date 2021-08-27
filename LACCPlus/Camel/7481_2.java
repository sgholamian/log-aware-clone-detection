//,temp,RestSwaggerSupport.java,215,288,temp,RestOpenApiSupport.java,381,456
//,3
public class xxx {
    public void renderResourceListing(
            CamelContext camelContext, RestApiResponseAdapter response,
            BeanConfig openApiConfig, String contextId, String route, boolean json,
            boolean yaml, Map<String, Object> headers, ClassResolver classResolver,
            RestConfiguration configuration)
            throws Exception {
        LOG.trace("renderResourceListing");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        if (cors) {
            setupCorsHeaders(response, configuration.getCorsHeaders());
        }

        List<RestDefinition> rests;
        if (camelContext.getName().equals(contextId)) {
            rests = getRestDefinitions(camelContext);
        } else {
            rests = getRestDefinitions(camelContext, contextId);
        }

        if (rests != null) {
            final Map<String, Object> apiProperties = configuration.getApiProperties() != null
                    ? configuration.getApiProperties() : new HashMap<>();
            if (json) {
                response.setHeader(Exchange.CONTENT_TYPE, (String) apiProperties
                        .getOrDefault("api.specification.contentType.json", "application/json"));

                // read the rest-dsl into openApi model
                OasDocument openApi = reader.read(camelContext, rests, route, openApiConfig, contextId, classResolver);
                if (configuration.isUseXForwardHeaders()) {
                    setupXForwardedHeaders(openApi, headers);
                }

                if (!configuration.isApiVendorExtension()) {
                    clearVendorExtensions(openApi);
                }

                Object dump = io.apicurio.datamodels.Library.writeNode(openApi);
                byte[] bytes = mapper.writeValueAsBytes(dump);
                int len = bytes.length;
                response.setHeader(Exchange.CONTENT_LENGTH, "" + len);

                response.writeBytes(bytes);
            } else {
                response.setHeader(Exchange.CONTENT_TYPE, (String) apiProperties
                        .getOrDefault("api.specification.contentType.yaml", "text/yaml"));

                // read the rest-dsl into openApi model
                OasDocument openApi = reader.read(camelContext, rests, route, openApiConfig, contextId, classResolver);
                if (configuration.isUseXForwardHeaders()) {
                    setupXForwardedHeaders(openApi, headers);
                }

                if (!configuration.isApiVendorExtension()) {
                    clearVendorExtensions(openApi);
                }

                Object dump = io.apicurio.datamodels.Library.writeNode(openApi);
                byte[] jsonData = mapper.writeValueAsBytes(dump);

                // json to yaml
                JsonNode node = mapper.readTree(jsonData);
                byte[] bytes = new YAMLMapper().writeValueAsString(node).getBytes();

                int len = bytes.length;
                response.setHeader(Exchange.CONTENT_LENGTH, "" + len);

                response.writeBytes(bytes);
            }
        } else {
            response.noContent();
        }
    }

};