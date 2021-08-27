//,temp,Athena2Producer.java,299,308,temp,Athena2Producer.java,288,297
//,3
public class xxx {
    private Integer determineMaxResults(final Exchange exchange) {
        Integer maxResults = exchange.getIn().getHeader(Athena2Constants.MAX_RESULTS, Integer.class);

        if (ObjectHelper.isEmpty(maxResults)) {
            maxResults = getConfiguration().getMaxResults();
            LOG.trace("AWS Athena max results is missing, using default one [{}]", maxResults);
        }

        return maxResults;
    }

};