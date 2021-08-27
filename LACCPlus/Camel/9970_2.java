//,temp,WeatherProducer.java,46,82,temp,WeatherConsumer.java,53,87
//,3
public class xxx {
    @Override
    protected int poll() throws Exception {
        LOG.debug("Going to execute the Weather query {}", query);
        HttpClient httpClient = getEndpoint().getConfiguration().getHttpClient();
        HttpGet getMethod = new HttpGet(query);
        try {
            HttpResponse response = httpClient.execute(getMethod);
            if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                LOG.warn("HTTP call for weather returned error status code {} - {} as a result with query: {}", status,
                        response.getStatusLine().getStatusCode(), query);
                return 0;
            }
            String weather = EntityUtils.toString(response.getEntity(), "UTF-8");
            LOG.debug("Got back the Weather information {}", weather);
            if (ObjectHelper.isEmpty(weather)) {
                // empty response
                return 0;
            }

            Exchange exchange = getEndpoint().createExchange();
            String header = getEndpoint().getConfiguration().getHeaderName();
            if (header != null) {
                exchange.getIn().setHeader(header, weather);
            } else {
                exchange.getIn().setBody(weather);
            }
            exchange.getIn().setHeader(WeatherConstants.WEATHER_QUERY, query);

            getProcessor().process(exchange);

            return 1;
        } finally {
            getMethod.releaseConnection();
        }
    }

};