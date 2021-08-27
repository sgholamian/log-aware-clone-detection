//,temp,Athena2Producer.java,299,308,temp,Athena2Producer.java,288,297
//,3
public class xxx {
    private boolean determineIncludeTrace(final Exchange exchange) {
        Boolean includeTrace = exchange.getIn().getHeader(Athena2Constants.INCLUDE_TRACE, Boolean.class);

        if (ObjectHelper.isEmpty(includeTrace)) {
            includeTrace = getConfiguration().isIncludeTrace();
            LOG.trace("AWS Athena include trace is missing, using default one [{}]", includeTrace);
        }

        return includeTrace;
    }

};