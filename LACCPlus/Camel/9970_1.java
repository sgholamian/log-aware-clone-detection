//,temp,WeatherProducer.java,46,82,temp,WeatherConsumer.java,53,87
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        String q = query;
        String location = exchange.getIn().getHeader(WeatherConstants.WEATHER_LOCATION, String.class);
        if (location != null) {
            q = getEndpoint().getWeatherQuery().getQuery(location);
        }

        HttpClient httpClient = getEndpoint().getConfiguration().getHttpClient();
        HttpGet method = new HttpGet(q);
        try {
            LOG.debug("Going to execute the Weather query {}", q);
            HttpResponse response = httpClient.execute(method);
            if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                throw new IllegalStateException(
                        "Got the invalid http status value '" + response.getStatusLine().getStatusCode()
                                                + "' as the result of the query '" + query + "'");
            }
            String weather = EntityUtils.toString(response.getEntity(), "UTF-8");
            LOG.debug("Got back the Weather information {}", weather);

            if (ObjectHelper.isEmpty(weather)) {
                throw new IllegalStateException(
                        "Got the unexpected value '" + weather + "' as the result of the query '" + q + "'");
            }

            String header = getEndpoint().getConfiguration().getHeaderName();
            if (header != null) {
                exchange.getIn().setHeader(header, weather);
            } else {
                exchange.getIn().setBody(weather);
            }
            exchange.getIn().setHeader(WeatherConstants.WEATHER_QUERY, q);
        } finally {
            method.releaseConnection();
        }
    }

};