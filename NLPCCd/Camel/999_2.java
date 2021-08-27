//,temp,sample_5032.java,2,14,temp,sample_5033.java,2,15
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
RestletEndpoint endpoint = (RestletEndpoint) getEndpoint();
final RestletBinding binding = endpoint.getRestletBinding();
Request request;
String resourceUri = buildUri(endpoint, exchange);
URI uri = new URI(resourceUri);
request = new Request(endpoint.getRestletMethod(), resourceUri);
binding.populateRestletRequestFromExchange(request, exchange);
loadCookies(exchange, uri, request);
Response response = client.handle(request);


log.info("received response synchronously for exchangeid");
}

};