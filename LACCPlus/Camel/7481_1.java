//,temp,RestSwaggerSupport.java,215,288,temp,RestOpenApiSupport.java,381,456
//,3
public class xxx {
    public void renderResourceListing(
            CamelContext camelContext, RestApiResponseAdapter response, BeanConfig swaggerConfig, String contextId,
            String route, boolean json, boolean yaml,
            Map<String, Object> headers, ClassResolver classResolver, RestConfiguration configuration)
            throws Exception {
        LOG.trace("renderResourceListing");

        ObjectMapper mapper = Json.mapper();
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
            final Map<String, Object> apiProperties
                    = configuration.getApiProperties() != null ? configuration.getApiProperties() : new HashMap<>();
            if (json) {
                response.setHeader(Exchange.CONTENT_TYPE,
                        (String) apiProperties.getOrDefault("api.specification.contentType.json", "application/json"));

                // read the rest-dsl into swagger model
                Swagger swagger = reader.read(rests, route, swaggerConfig, contextId, classResolver);
                if (configuration.isUseXForwardHeaders()) {
                    setupXForwardedHeaders(swagger, headers);
                }

                if (!configuration.isApiVendorExtension()) {
                    clearVendorExtensions(swagger);
                }

                byte[] bytes = mapper.writeValueAsBytes(swagger);

                int len = bytes.length;
                response.setHeader(Exchange.CONTENT_LENGTH, "" + len);

                response.writeBytes(bytes);
            } else {
                response.setHeader(Exchange.CONTENT_TYPE,
                        (String) apiProperties.getOrDefault("api.specification.contentType.yaml", "text/yaml"));

                // read the rest-dsl into swagger model
                Swagger swagger = reader.read(rests, route, swaggerConfig, contextId, classResolver);
                if (configuration.isUseXForwardHeaders()) {
                    setupXForwardedHeaders(swagger, headers);
                }

                if (!configuration.isApiVendorExtension()) {
                    clearVendorExtensions(swagger);
                }

                byte[] jsonData = mapper.writeValueAsBytes(swagger);

                // json to yaml
                JsonNode node = mapper.readTree(jsonData);
                byte[] bytes = Yaml.mapper().writerWithDefaultPrettyPrinter().writeValueAsBytes(node);

                int len = bytes.length;
                response.setHeader(Exchange.CONTENT_LENGTH, "" + len);

                response.writeBytes(bytes);
            }
        } else {
            response.noContent();
        }
    }

};