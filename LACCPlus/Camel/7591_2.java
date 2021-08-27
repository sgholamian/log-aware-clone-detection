//,temp,Athena2Producer.java,332,341,temp,Athena2Producer.java,321,330
//,2
public class xxx {
    private String determineClientRequestToken(final Exchange exchange) {
        String clientRequestToken = exchange.getIn().getHeader(Athena2Constants.CLIENT_REQUEST_TOKEN, String.class);

        if (ObjectHelper.isEmpty(clientRequestToken)) {
            clientRequestToken = getConfiguration().getClientRequestToken();
            LOG.trace("AWS Athena client request token is missing, using default one [{}]", clientRequestToken);
        }

        return clientRequestToken;
    }

};