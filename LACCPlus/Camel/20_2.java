//,temp,Athena2Producer.java,370,380,temp,Athena2Producer.java,310,319
//,2
public class xxx {
    private String determineNextToken(final Exchange exchange) {
        String nextToken = exchange.getIn().getHeader(Athena2Constants.NEXT_TOKEN, String.class);

        if (ObjectHelper.isEmpty(nextToken)) {
            nextToken = getConfiguration().getNextToken();
            LOG.trace("AWS Athena next token is missing, using default one [{}]", nextToken);
        }

        return nextToken;
    }

};