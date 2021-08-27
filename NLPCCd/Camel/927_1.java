//,temp,sample_4249.java,2,15,temp,sample_3359.java,2,11
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
String toUsername = user;
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(TwitterConstants.TWITTER_USER, String.class))) {
toUsername = exchange.getIn().getHeader(TwitterConstants.TWITTER_USER, String.class);
}
String text = exchange.getIn().getBody(String.class);
if (toUsername.isEmpty()) {
throw new CamelExchangeException("Username not configured on TwitterEndpoint", exchange);
} else {


log.info("sending to message");
}
}

};