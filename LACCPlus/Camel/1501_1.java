//,temp,GeoCoderProxyTest.java,47,59,temp,GeoCoderProxyTest.java,34,45
//,3
public class xxx {
    @Test
    public void testGeoCoderWithAuth() throws Exception {
        GeoCoderEndpoint endpoint = context.getEndpoint("geocoder:address:current?headersOnly=true&proxyHost=localhost"
                                                        + "&proxyPort=8888&proxyAuthMethod=Basic&proxyAuthUsername=proxy&proxyAuthPassword=proxy&apiKey="
                                                        + getApiKey(),
                GeoCoderEndpoint.class);

        GeoApiContext context = endpoint.createGeoApiContext();
        GeocodingApiRequest geocodingApiRequest = GeocodingApi.reverseGeocode(context, new LatLng(45.4643, 9.1895));
        GeocodingResult[] results = geocodingApiRequest.await();

        LOG.info("Response {}", results);
    }

};